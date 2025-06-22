package com.disaster.service.impl;

import com.disaster.dto.GeocodeResponse;
import com.disaster.service.GoogleMapsService;
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
public class GoogleMapsServiceImpl implements GoogleMapsService {

    private final WebClient webClient;

    @Value("${google.maps.api.key}")
    private String googleMapsApiKey;

    @Override
    public GeocodeResponse geocode(String locationName) {
        log.info("Geocoding location: {}", locationName);
        
        try {
            // For now, implement mock geocoding with predefined coordinates
            // In a real implementation, this would call the Google Maps Geocoding API
            Map<String, GeocodeResponse> mockGeocoding = Map.of(
                "Manhattan, NYC", new GeocodeResponse("Manhattan, NYC", 40.7128, -74.0060),
                "Lower East Side, NYC", new GeocodeResponse("Lower East Side, NYC", 40.7142, -73.9864),
                "East Village, NYC", new GeocodeResponse("East Village, NYC", 40.7265, -73.9815),
                "Brooklyn, NYC", new GeocodeResponse("Brooklyn, NYC", 40.6782, -73.9442),
                "Queens, NYC", new GeocodeResponse("Queens, NYC", 40.7282, -73.7949)
            );
            
            GeocodeResponse response = mockGeocoding.getOrDefault(locationName, 
                new GeocodeResponse(locationName, 40.7128, -74.0060)); // Default to Manhattan
            
            log.info("Geocoded location: {} -> lat: {}, lng: {}", 
                    locationName, response.getLatitude(), response.getLongitude());
            return response;
            
        } catch (Exception e) {
            log.error("Error geocoding location: {}", locationName, e);
            return new GeocodeResponse(locationName, 40.7128, -74.0060); // Fallback to Manhattan
        }
    }
} 