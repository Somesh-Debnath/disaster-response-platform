package com.disaster.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDisasterRequest {

    @Size(max = 255)
    private String title;

    @Size(max = 255)
    private String locationName;

    private String description;

    private List<String> tags;
} 