package com.disaster.service.impl;

import com.disaster.dto.GeocodeRequest;
import com.disaster.dto.GeocodeResponse;
import com.disaster.service.GeminiService;
import com.disaster.service.GeocodingService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeocodingServiceImpl implements GeocodingService {

    private final GeminiService geminiService;
    private final WebClient webClient;

    @Value("${mapbox.api.key}")
    private String mapboxApiKey;

    @Value("${mapbox.geocoding.url}")
    private String mapboxGeocodingUrl;

    @Override
    public GeocodeResponse geocodeLocation(GeocodeRequest request) {
        log.info("Geocoding location using Mapbox: {}", request.getLocationName());
        try {
            JsonNode responseNode = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path(mapboxGeocodingUrl + "/" + request.getLocationName() + ".json")
                            .queryParam("access_token", mapboxApiKey)
                            .queryParam("limit", 1)
                            .build())
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

            if (responseNode != null && responseNode.has("features") && responseNode.get("features").size() > 0) {
                JsonNode firstFeature = responseNode.get("features").get(0);
                JsonNode center = firstFeature.get("center");
                double longitude = center.get(0).asDouble();
                double latitude = center.get(1).asDouble();
                log.info("Geocoded location: {} -> lat: {}, lng: {}", request.getLocationName(), latitude, longitude);
                return new GeocodeResponse(request.getLocationName(), latitude, longitude);
            } else {
                log.warn("Could not geocode location: {}. No features found.", request.getLocationName());
                return new GeocodeResponse(request.getLocationName(), 0.0, 0.0);
            }
        } catch (Exception e) {
            log.error("Error geocoding location with Mapbox: {}", request.getLocationName(), e);
            // Return fallback coordinates
            return new GeocodeResponse(request.getLocationName(), 40.7128, -74.0060);
        }
    }

    @Override
    public GeocodeResponse extractAndGeocodeLocation(GeocodeRequest request) {
        log.info("Extracting and geocoding location from text: {}", request.getLocationName());
        
        try {
            String extractedLocation = geminiService.extractLocation(request.getLocationName());
            log.info("Extracted location: {}", extractedLocation);
            
            GeocodeRequest geocodeRequest = new GeocodeRequest(extractedLocation);
            return geocodeLocation(geocodeRequest);
            
        } catch (Exception e) {
            log.error("Error extracting and geocoding location: {}", request.getLocationName(), e);
            // Return mock coordinates for Manhattan as fallback
            return new GeocodeResponse("Manhattan, NYC", 40.7128, -74.0060);
        }
    }
} 