package com.disaster.service.impl;

import com.disaster.dto.OfficialUpdateDto;
import com.disaster.service.OfficialUpdatesService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class OfficialUpdatesServiceImpl implements OfficialUpdatesService {

    private final Map<UUID, List<OfficialUpdateDto>> mockUpdates = new HashMap<>();

    public OfficialUpdatesServiceImpl() {
        initializeMockData();
    }

    @Override
    public List<OfficialUpdateDto> getOfficialUpdates(UUID disasterId) {
        log.info("Fetching official updates for disaster: {}", disasterId);
        
        // In a real implementation, this would scrape actual government websites
        // For now, return mock data
        return mockUpdates.getOrDefault(disasterId, new ArrayList<>());
    }

    @Override
    public void refreshOfficialUpdates(UUID disasterId) {
        log.info("Refreshing official updates for disaster: {}", disasterId);
        
        try {
            // In a real implementation, this would scrape websites like:
            // - FEMA: https://www.fema.gov
            // - Red Cross: https://www.redcross.org
            // - NYC Emergency Management: https://www1.nyc.gov/site/em/index.page
            
            // For now, just log the action
            log.info("Would scrape official websites for disaster: {}", disasterId);
            
        } catch (Exception e) {
            log.error("Error refreshing official updates for disaster: {}", disasterId, e);
        }
    }

    private void initializeMockData() {
        // Mock official updates for flood disasters
        List<OfficialUpdateDto> floodUpdates = Arrays.asList(
            createMockUpdate("FEMA", "Flood Emergency Declaration", 
                "FEMA has declared a major disaster for NYC flood areas. Federal assistance is now available.", 
                "https://www.fema.gov/news-release/2024/01/15/flood-emergency-declaration-nyc", "high"),
            createMockUpdate("Red Cross", "Emergency Shelters Open", 
                "Red Cross has opened emergency shelters in Manhattan and Brooklyn for flood victims.", 
                "https://www.redcross.org/local/new-york/news-and-events/news/emergency-shelters-open.html", "high"),
            createMockUpdate("NYC Emergency Management", "Road Closures Update", 
                "Multiple roads remain closed due to flooding. Avoid travel in affected areas.", 
                "https://www1.nyc.gov/site/em/alerts/road-closures.page", "normal"),
            createMockUpdate("NYC Health Department", "Health Advisory", 
                "Health advisory issued for flood-affected areas. Avoid contact with floodwater.", 
                "https://www1.nyc.gov/site/doh/health/health-topics/flood-safety.page", "normal")
        );
        
        // Mock updates for earthquake disasters
        List<OfficialUpdateDto> earthquakeUpdates = Arrays.asList(
            createMockUpdate("USGS", "Earthquake Assessment", 
                "USGS has completed assessment of the recent earthquake. No major structural damage reported.", 
                "https://earthquake.usgs.gov/earthquakes/eventpage/ny2024abc123", "normal"),
            createMockUpdate("NYC Building Department", "Building Safety Inspection", 
                "Building safety inspections are ongoing in affected areas.", 
                "https://www1.nyc.gov/site/buildings/safety/earthquake-safety.page", "high")
        );
        
        // Store mock data with dummy disaster IDs
        mockUpdates.put(UUID.randomUUID(), floodUpdates);
        mockUpdates.put(UUID.randomUUID(), earthquakeUpdates);
    }

    private OfficialUpdateDto createMockUpdate(String source, String title, String content, String url, String priority) {
        OfficialUpdateDto update = new OfficialUpdateDto();
        update.setId(UUID.randomUUID());
        update.setSource(source);
        update.setTitle(title);
        update.setContent(content);
        update.setUrl(url);
        update.setPriority(priority);
        update.setPublishedAt(LocalDateTime.now().minusHours(new Random().nextInt(24)));
        update.setCategory("emergency");
        update.setContactInfo("1-800-EMERGENCY");
        return update;
    }
} 