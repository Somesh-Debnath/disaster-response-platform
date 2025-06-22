package com.disaster.controller;

import com.disaster.dto.SocialMediaReportDto;
import com.disaster.service.SocialMediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/disasters/{disasterId}/social-media")
@RequiredArgsConstructor
public class SocialMediaController {

    private final SocialMediaService socialMediaService;

    @GetMapping
    public ResponseEntity<List<SocialMediaReportDto>> getSocialMediaReports(
            @PathVariable UUID disasterId,
            @RequestParam(required = false) String keyword) {
        List<SocialMediaReportDto> reports = socialMediaService.getSocialMediaReports(disasterId, keyword);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/mock")
    public ResponseEntity<List<SocialMediaReportDto>> getMockSocialMediaReports(
            @RequestParam(required = false) String keyword) {
        List<SocialMediaReportDto> reports = socialMediaService.getMockSocialMediaReports(keyword);
        return ResponseEntity.ok(reports);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refreshSocialMediaReports(@PathVariable UUID disasterId) {
        socialMediaService.refreshSocialMediaReports(disasterId);
        return ResponseEntity.ok().build();
    }
} 