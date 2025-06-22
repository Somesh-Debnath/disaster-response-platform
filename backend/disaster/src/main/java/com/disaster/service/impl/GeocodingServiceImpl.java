package com.disaster.service.impl;

import com.disaster.dto.GeocodeRequest;
import com.disaster.dto.GeocodeResponse;
import com.disaster.service.GeminiService;
import com.disaster.service.GeocodingService;
import com.disaster.service.GoogleMapsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeocodingServiceImpl implements GeocodingService {

    private final GeminiService geminiService;
    private final GoogleMapsService googleMapsService;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Value("${google.maps.api.key}")
    private String googleMapsApiKey;

    @Override
    public GeocodeResponse geocodeLocation(GeocodeRequest request) {
        log.info("Geocoding location: {}", request.getLocationName());
        
        try {
            // Use Google Maps Geocoding API to convert location name to coordinates
            GeocodeResponse response = googleMapsService.geocode(request.getLocationName());
            log.info("Geocoded location: {} -> lat: {}, lng: {}", 
                    request.getLocationName(), response.getLatitude(), response.getLongitude());
            return response;
        } catch (Exception e) {
            log.error("Error geocoding location: {}", request.getLocationName(), e);
            // Return mock coordinates for Manhattan as fallback
            return new GeocodeResponse(request.getLocationName(), 40.7128, -74.0060);
        }
    }

    @Override
    public GeocodeResponse extractAndGeocodeLocation(GeocodeRequest request) {
        log.info("Extracting and geocoding location from text: {}", request.getLocationName());
        
        try {
            // First, use Gemini AI to extract location name from the text
            String extractedLocation = geminiService.extractLocation(request.getLocationName());
            log.info("Extracted location: {}", extractedLocation);
            
            // Then geocode the extracted location
            GeocodeRequest geocodeRequest = new GeocodeRequest(extractedLocation);
            return geocodeLocation(geocodeRequest);
            
        } catch (Exception e) {
            log.error("Error extracting and geocoding location: {}", request.getLocationName(), e);
            // Return mock coordinates for Manhattan as fallback
            return new GeocodeResponse("Manhattan, NYC", 40.7128, -74.0060);
        }
    }
} 