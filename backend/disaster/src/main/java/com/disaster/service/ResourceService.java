package com.disaster.service;

import com.disaster.dto.ResourceDto;

import java.util.List;
import java.util.UUID;

public interface ResourceService {
    List<ResourceDto> getResourcesByDisaster(UUID disasterId);
    List<ResourceDto> getResourcesNearLocation(UUID disasterId, Double lat, Double lon, Double radiusKm);
    List<ResourceDto> getNearbyResources(Double lat, Double lon, Double radiusKm);
    ResourceDto createResource(UUID disasterId, ResourceDto resourceDto);
} 