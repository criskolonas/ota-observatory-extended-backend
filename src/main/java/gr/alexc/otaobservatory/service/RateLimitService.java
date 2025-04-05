package gr.alexc.otaobservatory.service;

import gr.alexc.otaobservatory.repository.ota.BucketStateRepository;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.BucketState;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RateLimitService {

    @Autowired
    private BucketStateRepository bucketStateRepository;

    public Bucket resolveBucket(String bucketKey) {
        Optional<BucketState> bucketStateOptional = bucketStateRepository.findByBucketKey(bucketKey);

        if (bucketStateOptional.isPresent()) {
            // Deserialize existing bucket
            BucketState bucketState = bucketStateOptional.get();
            try {
                return BucketSerializationUtils.deserialize(bucketState.getBucketData());
            } catch (IOException e) {
                throw new RuntimeException("Failed to deserialize bucket", e);
            }
        } else {
            // Create a new bucket if not found
            Bucket bucket = createNewBucket();
            saveBucketState(bucketKey, bucket);
            return bucket;
        }
    }

    public void saveBucketState(String bucketKey, Bucket bucket) {
        try {
            byte[] serializedBucket = BucketSerializationUtils.serialize(bucket);
            BucketState bucketState = new BucketState();
            bucketState.setBucketKey(bucketKey);
            bucketState.setBucketData(serializedBucket);
            bucketState.setLastUpdated(LocalDateTime.now());
            bucketStateRepository.save(bucketState);
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize bucket", e);
        }
    }

    private Bucket createNewBucket() {
        // Define rate-limiting rules
        Refill refill = Refill.greedy(10, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(10, refill);

        // Create a new bucket
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}

