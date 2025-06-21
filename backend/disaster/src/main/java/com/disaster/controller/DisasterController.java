package com.disaster.controller;

import com.disaster.dto.CreateDisasterRequest;
import com.disaster.dto.DisasterDto;
import com.disaster.dto.UpdateDisasterRequest;
import com.disaster.service.DisasterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/disasters")
@RequiredArgsConstructor
public class DisasterController {

    private final DisasterService disasterService;

    @GetMapping
    public ResponseEntity<List<DisasterDto>> getAllDisasters(@RequestParam(required = false) String tag) {
        List<DisasterDto> disasters = disasterService.getAllDisasters(tag);
        return ResponseEntity.ok(disasters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisasterDto> getDisasterById(@PathVariable UUID id) {
        DisasterDto disaster = disasterService.getDisasterById(id);
        return ResponseEntity.ok(disaster);
    }

    @PostMapping
    public ResponseEntity<DisasterDto> createDisaster(@Valid @RequestBody CreateDisasterRequest request) {
        DisasterDto newDisaster = disasterService.createDisaster(request);
        return new ResponseEntity<>(newDisaster, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisasterDto> updateDisaster(@PathVariable UUID id, @Valid @RequestBody UpdateDisasterRequest request) {
        DisasterDto updatedDisaster = disasterService.updateDisaster(id, request);
        return ResponseEntity.ok(updatedDisaster);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisaster(@PathVariable UUID id) {
        disasterService.deleteDisaster(id);
        return ResponseEntity.noContent().build();
    }
} 