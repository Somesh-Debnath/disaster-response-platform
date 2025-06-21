package com.disaster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisasterDto {
    private UUID id;
    private String title;
    private String locationName;
    private Double latitude;
    private Double longitude;
    private String description;
    private List<String> tags;
    private String ownerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // The audit trail is sent as a raw JSON string
    private String auditTrail;
} 