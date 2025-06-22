package com.disaster.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfficialUpdateDto {
    private UUID id;
    private UUID disasterId;
    private String source;
    private String title;
    private String content;
    private String url;
    private LocalDateTime publishedAt;
    private String priority;
    private String category;
    private String contactInfo;
} 