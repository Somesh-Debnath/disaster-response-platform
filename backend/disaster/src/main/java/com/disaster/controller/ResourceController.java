package com.disaster.controller;

import com.disaster.dto.ResourceDto;
import com.disaster.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/disasters/{disasterId}/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    @GetMapping
    public ResponseEntity<List<ResourceDto>> getResources(
            @PathVariable UUID disasterId,
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lon,
            @RequestParam(required = false, defaultValue = "10.0") Double radiusKm) {
        
        List<ResourceDto> resources;
        if (lat != null && lon != null) {
            resources = resourceService.getResourcesNearLocation(disasterId, lat, lon, radiusKm);
        } else {
            resources = resourceService.getResourcesByDisaster(disasterId);
        }
        return ResponseEntity.ok(resources);
    }

    @PostMapping
    public ResponseEntity<ResourceDto> createResource(
            @PathVariable UUID disasterId,
            @RequestBody ResourceDto resourceDto) {
        ResourceDto createdResource = resourceService.createResource(disasterId, resourceDto);
        return ResponseEntity.ok(createdResource);
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<ResourceDto>> getNearbyResources(
            @RequestParam Double lat,
            @RequestParam Double lon,
            @RequestParam(defaultValue = "10.0") Double radiusKm) {
        List<ResourceDto> resources = resourceService.getNearbyResources(lat, lon, radiusKm);
        return ResponseEntity.ok(resources);
    }
} 