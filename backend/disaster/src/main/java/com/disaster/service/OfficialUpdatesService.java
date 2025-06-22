package com.disaster.service;

import com.disaster.dto.OfficialUpdateDto;

import java.util.List;
import java.util.UUID;

public interface OfficialUpdatesService {
    List<OfficialUpdateDto> getOfficialUpdates(UUID disasterId);
    void refreshOfficialUpdates(UUID disasterId);
} 