package com.disaster.controller;

import com.disaster.dto.OfficialUpdateDto;
import com.disaster.service.OfficialUpdatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/disasters/{disasterId}/official-updates")
@RequiredArgsConstructor
public class OfficialUpdatesController {

    private final OfficialUpdatesService officialUpdatesService;

    @GetMapping
    public ResponseEntity<List<OfficialUpdateDto>> getOfficialUpdates(@PathVariable UUID disasterId) {
        List<OfficialUpdateDto> updates = officialUpdatesService.getOfficialUpdates(disasterId);
        return ResponseEntity.ok(updates);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refreshOfficialUpdates(@PathVariable UUID disasterId) {
        officialUpdatesService.refreshOfficialUpdates(disasterId);
        return ResponseEntity.ok().build();
    }
} 