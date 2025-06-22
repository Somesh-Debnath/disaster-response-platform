package com.disaster.service;

import com.disaster.dto.SocialMediaReportDto;

import java.util.List;
import java.util.UUID;

public interface SocialMediaService {
    List<SocialMediaReportDto> getSocialMediaReports(UUID disasterId, String keyword);
    List<SocialMediaReportDto> getMockSocialMediaReports(String keyword);
    void refreshSocialMediaReports(UUID disasterId);
} 