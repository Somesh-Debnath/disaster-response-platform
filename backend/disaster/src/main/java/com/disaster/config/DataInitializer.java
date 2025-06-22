package com.disaster.config;

import com.disaster.entity.Disaster;
import com.disaster.entity.Resource;
import com.disaster.repository.DisasterRepository;
import com.disaster.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final DisasterRepository disasterRepository;
    private final ResourceRepository resourceRepository;
    private final GeometryFactory geometryFactory = new GeometryFactory();

    @Override
    public void run(String... args) throws Exception {
        log.info("Checking if sample data needs to be initialized...");
        
        // Only initialize if no disasters exist
        if (disasterRepository.count() == 0) {
            log.info("No disasters found. Initializing sample data...");
            
            // Create sample disasters
            Disaster floodDisaster = createFloodDisaster();
            Disaster earthquakeDisaster = createEarthquakeDisaster();
            
            disasterRepository.saveAll(Arrays.asList(floodDisaster, earthquakeDisaster));
            
            // Create sample resources
            List<Resource> resources = createSampleResources(floodDisaster, earthquakeDisaster);
            resourceRepository.saveAll(resources);
            
            log.info("Sample data initialized successfully!");
        } else {
            log.info("Database already contains data. Skipping initialization.");
        }
    }

    private Disaster createFloodDisaster() {
        Disaster disaster = new Disaster();
        disaster.setTitle("NYC Flood Emergency");
        disaster.setLocationName("Manhattan, NYC");
        disaster.setDescription("Heavy flooding affecting Lower Manhattan and East Village areas. Multiple roads closed and power outages reported.");
        disaster.setTags(Arrays.asList("flood", "urgent", "nyc"));
        disaster.setOwnerId("netrunnerX");
        
        // Set location coordinates for Manhattan
        Point location = geometryFactory.createPoint(new Coordinate(-74.0060, 40.7128));
        disaster.setLocation(location);
        
        return disaster;
    }

    private Disaster createEarthquakeDisaster() {
        Disaster disaster = new Disaster();
        disaster.setTitle("Downtown Earthquake");
        disaster.setLocationName("Downtown, NYC");
        disaster.setDescription("Moderate earthquake felt in downtown area. Building inspections ongoing.");
        disaster.setTags(Arrays.asList("earthquake", "downtown"));
        disaster.setOwnerId("reliefAdmin");
        
        // Set location coordinates for Downtown
        Point location = geometryFactory.createPoint(new Coordinate(-74.0130, 40.7080));
        disaster.setLocation(location);
        
        return disaster;
    }

    private List<Resource> createSampleResources(Disaster floodDisaster, Disaster earthquakeDisaster) {
        return Arrays.asList(
            createResource(floodDisaster, "Red Cross Shelter", "Lower East Side, NYC", "shelter", -73.9864, 40.7142),
            createResource(floodDisaster, "Emergency Medical Center", "East Village, NYC", "medical", -73.9815, 40.7265),
            createResource(floodDisaster, "Food Distribution Center", "Manhattan, NYC", "food", -74.0060, 40.7128),
            createResource(earthquakeDisaster, "Building Inspection Team", "Downtown, NYC", "inspection", -74.0130, 40.7080),
            createResource(earthquakeDisaster, "Emergency Response Unit", "Financial District, NYC", "emergency", -74.0100, 40.7050)
        );
    }

    private Resource createResource(Disaster disaster, String name, String locationName, String type, double lon, double lat) {
        Resource resource = new Resource();
        resource.setDisaster(disaster);
        resource.setName(name);
        resource.setLocationName(locationName);
        resource.setType(type);
        
        Point location = geometryFactory.createPoint(new Coordinate(lon, lat));
        resource.setLocation(location);
        
        return resource;
    }
} 