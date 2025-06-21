package com.disaster.service;

import com.disaster.dto.CreateDisasterRequest;
import com.disaster.dto.DisasterDto;
import com.disaster.dto.UpdateDisasterRequest;

import java.util.List;
import java.util.UUID;

public interface DisasterService {
    List<DisasterDto> getAllDisasters(String tag);
    DisasterDto getDisasterById(UUID id);
    DisasterDto createDisaster(CreateDisasterRequest request);
    DisasterDto updateDisaster(UUID id, UpdateDisasterRequest request);
    void deleteDisaster(UUID id);
} 