package com.disaster.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class RateLimitService {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public boolean allowRequest(String apiKey, int requestsPerMinute) {
        Bucket bucket = buckets.computeIfAbsent(apiKey, k -> createBucket(requestsPerMinute));
        
        boolean allowed = bucket.tryConsume(1);
        if (!allowed) {
            log.warn("Rate limit exceeded for API key: {}", apiKey);
        }
        return allowed;
    }

    public boolean allowGeminiRequest() {
        return allowRequest("gemini", 60); // 60 requests per minute for Gemini API
    }

    public boolean allowGoogleMapsRequest() {
        return allowRequest("google-maps", 100); // 100 requests per minute for Google Maps API
    }

    public boolean allowSocialMediaRequest() {
        return allowRequest("social-media", 300); // 300 requests per minute for social media APIs
    }

    private Bucket createBucket(int requestsPerMinute) {
        Bandwidth limit = Bandwidth.classic(requestsPerMinute, 
            Refill.greedy(requestsPerMinute, Duration.ofMinutes(1)));
        return Bucket.builder().addLimit(limit).build();
    }

    public void resetBucket(String apiKey) {
        buckets.remove(apiKey);
        log.info("Reset rate limit bucket for API key: {}", apiKey);
    }
} 