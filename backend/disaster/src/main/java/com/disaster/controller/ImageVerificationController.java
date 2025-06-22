package com.disaster.controller;

import com.disaster.dto.VerifyImageRequest;
import com.disaster.dto.VerifyImageResponse;
import com.disaster.service.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/disasters/{disasterId}/verify-image")
@RequiredArgsConstructor
public class ImageVerificationController {

    private final GeminiService geminiService;

    @PostMapping
    public ResponseEntity<VerifyImageResponse> verifyImage(
            @PathVariable UUID disasterId,
            @RequestBody VerifyImageRequest request) {
        
        String verificationResult = geminiService.verifyImage(request.getImageUrl());
        
        VerifyImageResponse response = new VerifyImageResponse();
        response.setDisasterId(disasterId);
        response.setImageUrl(request.getImageUrl());
        response.setVerificationResult(verificationResult);
        response.setVerified(verificationResult.startsWith("verified"));
        
        return ResponseEntity.ok(response);
    }
} 