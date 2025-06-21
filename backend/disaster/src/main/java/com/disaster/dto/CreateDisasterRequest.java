package com.disaster.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDisasterRequest {

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255)
    private String title;

    @Size(max = 255)
    private String locationName;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotEmpty(message = "At least one tag is required")
    private List<String> tags;
} 