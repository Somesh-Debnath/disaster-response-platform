package com.disaster.service.impl;

import com.disaster.service.GeminiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeminiServiceImpl implements GeminiService {

    private final WebClient webClient;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Override
    public String extractLocation(String text) {
        log.info("Extracting location from text: {}", text);
        
        try {
            // For now, implement a simple keyword-based location extraction
            // In a real implementation, this would call the Gemini API
            if (text.toLowerCase().contains("manhattan") || text.toLowerCase().contains("nyc")) {
                return "Manhattan, NYC";
            } else if (text.toLowerCase().contains("lower east side")) {
                return "Lower East Side, NYC";
            } else if (text.toLowerCase().contains("east village")) {
                return "East Village, NYC";
            } else if (text.toLowerCase().contains("brooklyn")) {
                return "Brooklyn, NYC";
            } else if (text.toLowerCase().contains("queens")) {
                return "Queens, NYC";
            } else {
                return "Manhattan, NYC"; // Default fallback
            }
            
        } catch (Exception e) {
            log.error("Error extracting location from text: {}", text, e);
            return "Manhattan, NYC"; // Fallback location
        }
    }

    @Override
    public String verifyImage(String imageUrl) {
        log.info("Verifying image: {}", imageUrl);
        
        try {
            // For now, implement a simple verification logic
            // In a real implementation, this would call the Gemini API with image analysis
            if (imageUrl.contains("flood") || imageUrl.contains("disaster")) {
                return "verified - Image appears to show disaster context";
            } else if (imageUrl.contains("fake") || imageUrl.contains("edited")) {
                return "suspicious - Signs of potential manipulation detected";
            } else {
                return "unverified - Unable to determine authenticity";
            }
            
        } catch (Exception e) {
            log.error("Error verifying image: {}", imageUrl, e);
            return "unverified - Error during verification";
        }
    }
} 