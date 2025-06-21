package com.disaster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportDto {
    private UUID id;
    private UUID disasterId;
    private String userId;
    private String content;
    private String imageUrl;
    private String verificationStatus;
    private LocalDateTime createdAt;
} 