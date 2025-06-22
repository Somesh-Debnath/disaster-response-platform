package com.disaster.service;

import com.disaster.entity.Cache;
import com.disaster.repository.CacheRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CacheService {

    private final CacheRepository cacheRepository;
    private final ObjectMapper objectMapper;

    public <T> Optional<T> get(String key, Class<T> clazz) {
        try {
            Optional<Cache> cacheOpt = cacheRepository.findById(key);
            if (cacheOpt.isPresent()) {
                Cache cache = cacheOpt.get();
                if (cache.getExpiresAt().isAfter(LocalDateTime.now())) {
                    T value = objectMapper.readValue(cache.getValue(), clazz);
                    log.info("Cache hit for key: {}", key);
                    return Optional.of(value);
                } else {
                    log.info("Cache expired for key: {}", key);
                    cacheRepository.deleteById(key);
                }
            }
        } catch (Exception e) {
            log.error("Error retrieving from cache for key: {}", key, e);
        }
        return Optional.empty();
    }

    public <T> void put(String key, T value, int ttlHours) {
        try {
            String jsonValue = objectMapper.writeValueAsString(value);
            LocalDateTime expiresAt = LocalDateTime.now().plusHours(ttlHours);
            
            Cache cache = new Cache();
            cache.setKey(key);
            cache.setValue(jsonValue);
            cache.setExpiresAt(expiresAt);
            
            cacheRepository.save(cache);
            log.info("Cached value for key: {} with TTL: {} hours", key, ttlHours);
        } catch (JsonProcessingException e) {
            log.error("Error serializing value for cache key: {}", key, e);
        } catch (Exception e) {
            log.error("Error storing in cache for key: {}", key, e);
        }
    }

    public void delete(String key) {
        try {
            cacheRepository.deleteById(key);
            log.info("Deleted cache entry for key: {}", key);
        } catch (Exception e) {
            log.error("Error deleting cache entry for key: {}", key, e);
        }
    }

    public boolean exists(String key) {
        try {
            Optional<Cache> cacheOpt = cacheRepository.findById(key);
            if (cacheOpt.isPresent()) {
                Cache cache = cacheOpt.get();
                if (cache.getExpiresAt().isAfter(LocalDateTime.now())) {
                    return true;
                } else {
                    cacheRepository.deleteById(key);
                }
            }
        } catch (Exception e) {
            log.error("Error checking cache existence for key: {}", key, e);
        }
        return false;
    }
} 