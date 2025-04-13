package gr.alexc.otaobservatory.service;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RateLimiterService {

    private final Map<String, Bucket> buckets;
    private final BucketConfiguration bucketConfig;

    public RateLimiterService(Map<String, Bucket> buckets, BucketConfiguration bucketConfig) {
        this.buckets = buckets;
        this.bucketConfig = bucketConfig;
    }

    public boolean allowRequest(String key) {
        // Get or create bucket for each key
        Bucket bucket = buckets.computeIfAbsent(key, k -> Bucket.builder()
                .addLimit(bucketConfig.getBandwidths()[0])
                .build());

        return bucket.tryConsume(1); // Try to consume 1 token
    }
}