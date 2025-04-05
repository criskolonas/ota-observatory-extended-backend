package gr.alexc.otaobservatory.repository.ota;

import io.github.bucket4j.BucketState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BucketStateRepository extends JpaRepository<BucketState, Long> {
    Optional<BucketState> findByBucketKey(String bucketKey);
}