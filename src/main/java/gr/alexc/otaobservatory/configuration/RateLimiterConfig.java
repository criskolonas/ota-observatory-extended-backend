package gr.alexc.otaobservatory.configuration;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class RateLimiterConfig {

    // Simple in-memory storage for buckets
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Bean
    public Map<String, Bucket> buckets() {
        return buckets;
    }

    @Bean
    public BucketConfiguration bucketConfig() {
        // Allow 10 requests per minute
        return BucketConfiguration.builder()
                .addLimit(Bandwidth.classic(50, Refill.intervally(10, Duration.ofMinutes(1))))
                .build();
    }
}