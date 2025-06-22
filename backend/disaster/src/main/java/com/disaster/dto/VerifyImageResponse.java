package com.disaster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyImageResponse {
    private UUID disasterId;
    private String imageUrl;
    private String verificationResult;
    private boolean verified;
    private String analysis; // To hold details from Gemini
} 