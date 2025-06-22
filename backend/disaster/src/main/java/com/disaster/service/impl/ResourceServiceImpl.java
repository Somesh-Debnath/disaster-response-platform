package com.disaster.service.impl;

import com.disaster.dto.ResourceDto;
import com.disaster.entity.Disaster;
import com.disaster.entity.Resource;
import com.disaster.repository.DisasterRepository;
import com.disaster.repository.ResourceRepository;
import com.disaster.service.ResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;
    private final DisasterRepository disasterRepository;
    private final GeometryFactory geometryFactory = new GeometryFactory();

    @Override
    @Transactional(readOnly = true)
    public List<ResourceDto> getResourcesByDisaster(UUID disasterId) {
        log.info("Fetching resources for disaster: {}", disasterId);
        List<Resource> resources = resourceRepository.findByDisasterId(disasterId);
        return resources.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceDto> getResourcesNearLocation(UUID disasterId, Double lat, Double lon, Double radiusKm) {
        log.info("Fetching resources near location: lat={}, lon={}, radius={}km for disaster: {}", lat, lon, radiusKm, disasterId);
        
        Point searchPoint = geometryFactory.createPoint(new Coordinate(lon, lat));
        List<Resource> resources = resourceRepository.findResourcesNearLocation(disasterId, searchPoint, radiusKm * 1000); // Convert km to meters
        
        return resources.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResourceDto> getNearbyResources(Double lat, Double lon, Double radiusKm) {
        log.info("Fetching nearby resources: lat={}, lon={}, radius={}km", lat, lon, radiusKm);
        
        Point searchPoint = geometryFactory.createPoint(new Coordinate(lon, lat));
        List<Resource> resources = resourceRepository.findNearbyResources(searchPoint, radiusKm * 1000); // Convert km to meters
        
        return resources.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ResourceDto createResource(UUID disasterId, ResourceDto resourceDto) {
        log.info("Creating resource for disaster: {}", disasterId);
        
        Disaster disaster = disasterRepository.findById(disasterId)
                .orElseThrow(() -> new RuntimeException("Disaster not found with id: " + disasterId));
        
        Resource resource = new Resource();
        resource.setDisaster(disaster);
        resource.setName(resourceDto.getName());
        resource.setLocationName(resourceDto.getLocationName());
        resource.setType(resourceDto.getType());
        
        // Set location if coordinates are provided
        if (resourceDto.getLatitude() != null && resourceDto.getLongitude() != null) {
            Point location = geometryFactory.createPoint(new Coordinate(resourceDto.getLongitude(), resourceDto.getLatitude()));
            resource.setLocation(location);
        }
        
        Resource savedResource = resourceRepository.save(resource);
        log.info("Resource created: {} at location: {}", savedResource.getName(), savedResource.getLocationName());
        
        return toDto(savedResource);
    }

    private ResourceDto toDto(Resource resource) {
        ResourceDto dto = new ResourceDto();
        dto.setId(resource.getId());
        dto.setDisasterId(resource.getDisaster().getId());
        dto.setName(resource.getName());
        dto.setLocationName(resource.getLocationName());
        dto.setType(resource.getType());
        dto.setCreatedAt(resource.getCreatedAt());
        
        if (resource.getLocation() != null) {
            dto.setLatitude(resource.getLocation().getY());
            dto.setLongitude(resource.getLocation().getX());
        }
        
        return dto;
    }
} 