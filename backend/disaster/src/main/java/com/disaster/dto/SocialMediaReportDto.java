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
public class SocialMediaReportDto {
    private UUID id;
    private String post;
    private String user;
    private String platform;
    private String sentiment;
    private String priority;
    private LocalDateTime timestamp;
    private String location;
    private String hashtags;
    private Integer retweetCount;
    private Integer likeCount;
    private String verificationStatus;
} 