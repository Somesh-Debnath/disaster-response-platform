package com.disaster.service.impl;

import com.disaster.dto.SocialMediaReportDto;
import com.disaster.service.SocialMediaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SocialMediaServiceImpl implements SocialMediaService {

    private final Map<String, List<SocialMediaReportDto>> mockData = new HashMap<>();

    @PostConstruct
    public void setup() {
        initializeMockData();
    }

    @Override
    public List<SocialMediaReportDto> getSocialMediaReports(UUID disasterId, String keyword) {
        log.info("Fetching social media reports for disaster: {} with keyword: {}", disasterId, keyword);
        
        // In a real implementation, this would call Twitter API or Bluesky API
        // For now, return mock data
        List<SocialMediaReportDto> allReports = getMockSocialMediaReports(keyword);
        
        // Filter by disaster-specific keywords (in real implementation, this would be more sophisticated)
        return allReports.stream()
                .filter(report -> report.getPost().toLowerCase().contains("flood") || 
                                report.getPost().toLowerCase().contains("disaster") ||
                                report.getPost().toLowerCase().contains("emergency"))
                .collect(Collectors.toList());
    }

    @Override
    public List<SocialMediaReportDto> getMockSocialMediaReports(String keyword) {
        log.info("Fetching mock social media reports with keyword: {}", keyword);
        
        List<SocialMediaReportDto> allReports = new ArrayList<>();
        
        // Add flood-related reports
        allReports.addAll(mockData.getOrDefault("flood", new ArrayList<>()));
        allReports.addAll(mockData.getOrDefault("general", new ArrayList<>()));
        
        if (keyword != null && !keyword.isEmpty()) {
            return allReports.stream()
                    .filter(report -> report.getPost().toLowerCase().contains(keyword.toLowerCase()) ||
                                    report.getHashtags().toLowerCase().contains(keyword.toLowerCase()))
                    .collect(Collectors.toList());
        }
        
        return allReports;
    }

    @Override
    public void refreshSocialMediaReports(UUID disasterId) {
        log.info("Refreshing social media reports for disaster: {}", disasterId);
        // In a real implementation, this would trigger a new API call to Twitter/Bluesky
        // For now, just log the action
    }

    private void initializeMockData() {
        // Mock flood-related social media posts
        List<SocialMediaReportDto> floodReports = Arrays.asList(
            createMockReport("#floodrelief Need food and water in Lower East Side, NYC", "citizen1", "Twitter", "urgent", "Manhattan, NYC", "#floodrelief #NYC #emergency"),
            createMockReport("Shelter available at Red Cross center on 2nd Ave", "relief_worker", "Twitter", "normal", "Manhattan, NYC", "#shelter #floodrelief"),
            createMockReport("SOS: Trapped in apartment building on 5th St", "trapped_citizen", "Twitter", "urgent", "Manhattan, NYC", "#SOS #trapped #flood"),
            createMockReport("Medical supplies needed at community center", "health_worker", "Twitter", "high", "Manhattan, NYC", "#medical #supplies #floodrelief"),
            createMockReport("Power outage affecting 3 blocks in East Village", "utility_worker", "Twitter", "high", "Manhattan, NYC", "#poweroutage #EastVillage")
        );
        
        // Mock general disaster reports
        List<SocialMediaReportDto> generalReports = Arrays.asList(
            createMockReport("Earthquake felt in downtown area", "seismologist", "Twitter", "high", "Downtown", "#earthquake #alert"),
            createMockReport("Fire department responding to multiple calls", "fire_dept", "Twitter", "urgent", "City Center", "#fire #emergency"),
            createMockReport("Roads closed due to flooding", "traffic_control", "Twitter", "normal", "Highway 101", "#roadclosure #flooding")
        );
        
        mockData.put("flood", floodReports);
        mockData.put("general", generalReports);
    }

    private SocialMediaReportDto createMockReport(String post, String user, String platform, String priority, String location, String hashtags) {
        SocialMediaReportDto report = new SocialMediaReportDto();
        report.setId(UUID.randomUUID());
        report.setPost(post);
        report.setUser(user);
        report.setPlatform(platform);
        report.setPriority(priority);
        report.setLocation(location);
        report.setHashtags(hashtags);
        report.setTimestamp(LocalDateTime.now().minusMinutes(new Random().nextInt(60)));
        report.setRetweetCount(new Random().nextInt(100));
        report.setLikeCount(new Random().nextInt(500));
        report.setVerificationStatus("pending");
        
        // Simple sentiment analysis based on keywords
        if (post.toLowerCase().contains("sos") || post.toLowerCase().contains("urgent") || post.toLowerCase().contains("trapped")) {
            report.setSentiment("negative");
        } else if (post.toLowerCase().contains("available") || post.toLowerCase().contains("help")) {
            report.setSentiment("positive");
        } else {
            report.setSentiment("neutral");
        }
        
        return report;
    }
} 