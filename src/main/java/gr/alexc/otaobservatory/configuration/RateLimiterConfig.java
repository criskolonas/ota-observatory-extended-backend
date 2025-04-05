package gr.alexc.otaobservatory.configuration;
import io.github.bucket4j.BandwidthBuilder;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;

import java.time.Duration;

public class RateLimiterConfig {

    public static Bucket createAuthBucket() {
        Bucket bucket = Bucket.builder()
                .addLimit(Bandwidth.classic(100, Refill.greedy(100, Duration.ofMinutes(1))))
                .build();

        return bucket;
    }
}
