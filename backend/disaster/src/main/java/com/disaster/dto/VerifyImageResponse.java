package com.disaster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyImageResponse {
    private String imageUrl;
    private boolean authentic;
    private String analysis; // To hold details from Gemini
} 