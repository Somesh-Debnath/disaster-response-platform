package com.disaster.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeocodeResponse {
    private String locationName;
    private double latitude;
    private double longitude;
} 