package com.disaster.service.impl;

import com.disaster.dto.CreateDisasterRequest;
import com.disaster.dto.DisasterDto;
import com.disaster.dto.UpdateDisasterRequest;
import com.disaster.entity.Disaster;
import com.disaster.repository.DisasterRepository;
import com.disaster.service.DisasterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Coordinate;


import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DisasterServiceImpl implements DisasterService {

    private final DisasterRepository disasterRepository;
    private final ObjectMapper objectMapper;
    private final GeometryFactory geometryFactory = new GeometryFactory();

    @Override
    @Transactional(readOnly = true)
    public List<DisasterDto> getAllDisasters(String tag) {
        List<Disaster> disasters;
        if (tag != null && !tag.isEmpty()) {
            disasters = disasterRepository.findByTag(tag);
        } else {
            disasters = disasterRepository.findAll();
        }
        return disasters.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DisasterDto getDisasterById(UUID id) {
        return disasterRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Disaster not found with id: " + id));
    }

    @Override
    @Transactional
    public DisasterDto createDisaster(CreateDisasterRequest request) {
        Disaster disaster = new Disaster();
        disaster.setTitle(request.getTitle());
        disaster.setLocationName(request.getLocationName());
        disaster.setDescription(request.getDescription());
        disaster.setTags(request.getTags());
        disaster.setOwnerId("mock-admin"); // Mock owner for now
        
        try {
            disaster.setAuditTrail(objectMapper.writeValueAsString(Collections.singletonList("Initial creation")));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error creating audit trail", e);
        }
        
        Disaster savedDisaster = disasterRepository.save(disaster);
        return toDto(savedDisaster);
    }

    @Override
    @Transactional
    public DisasterDto updateDisaster(UUID id, UpdateDisasterRequest request) {
        Disaster disaster = disasterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disaster not found with id: " + id));

        // Update fields if they are provided in the request
        if (request.getTitle() != null) disaster.setTitle(request.getTitle());
        if (request.getLocationName() != null) disaster.setLocationName(request.getLocationName());
        if (request.getDescription() != null) disaster.setDescription(request.getDescription());
        if (request.getTags() != null) disaster.setTags(request.getTags());
        
        // Here you would add to the audit trail
        
        Disaster updatedDisaster = disasterRepository.save(disaster);
        return toDto(updatedDisaster);
    }

    @Override
    @Transactional
    public void deleteDisaster(UUID id) {
        if (!disasterRepository.existsById(id)) {
            throw new RuntimeException("Disaster not found with id: " + id);
        }
        disasterRepository.deleteById(id);
    }

    private DisasterDto toDto(Disaster disaster) {
        DisasterDto dto = new DisasterDto();
        dto.setId(disaster.getId());
        dto.setTitle(disaster.getTitle());
        dto.setLocationName(disaster.getLocationName());
        if (disaster.getLocation() != null) {
            dto.setLatitude(disaster.getLocation().getY());
            dto.setLongitude(disaster.getLocation().getX());
        }
        dto.setDescription(disaster.getDescription());
        dto.setTags(disaster.getTags());
        dto.setOwnerId(disaster.getOwnerId());
        dto.setCreatedAt(disaster.getCreatedAt());
        dto.setUpdatedAt(disaster.getUpdatedAt());
        dto.setAuditTrail(disaster.getAuditTrail());
        return dto;
    }
} 