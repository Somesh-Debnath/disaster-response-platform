package com.disaster.service;

import com.disaster.dto.GeocodeRequest;
import com.disaster.dto.GeocodeResponse;

public interface GeocodingService {
    GeocodeResponse geocodeLocation(GeocodeRequest request);
    GeocodeResponse extractAndGeocodeLocation(GeocodeRequest request);
} 