package com.disaster.controller;

import com.disaster.dto.GeocodeRequest;
import com.disaster.dto.GeocodeResponse;
import com.disaster.service.GeocodingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/geocode")
@RequiredArgsConstructor
public class GeocodingController {

    private final GeocodingService geocodingService;

    @PostMapping
    public ResponseEntity<GeocodeResponse> geocodeLocation(@RequestBody GeocodeRequest request) {
        GeocodeResponse response = geocodingService.geocodeLocation(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/extract")
    public ResponseEntity<GeocodeResponse> extractAndGeocodeLocation(@RequestBody GeocodeRequest request) {
        GeocodeResponse response = geocodingService.extractAndGeocodeLocation(request);
        return ResponseEntity.ok(response);
    }
} 