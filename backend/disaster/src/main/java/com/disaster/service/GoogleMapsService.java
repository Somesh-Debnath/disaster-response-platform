package com.disaster.service;

import com.disaster.dto.GeocodeResponse;

public interface GoogleMapsService {
    GeocodeResponse geocode(String locationName);
} 