package com.disaster.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper;

    public void broadcastDisasterUpdate(String disasterId, Object payload) {
        try {
            String message = objectMapper.writeValueAsString(payload);
            messagingTemplate.convertAndSend("/topic/disasters/" + disasterId, message);
            log.info("Broadcasted disaster update for disaster: {}", disasterId);
        } catch (Exception e) {
            log.error("Error broadcasting disaster update", e);
        }
    }

    public void broadcastSocialMediaUpdate(String disasterId, Object payload) {
        try {
            String message = objectMapper.writeValueAsString(payload);
            messagingTemplate.convertAndSend("/topic/disasters/" + disasterId + "/social-media", message);
            log.info("Broadcasted social media update for disaster: {}", disasterId);
        } catch (Exception e) {
            log.error("Error broadcasting social media update", e);
        }
    }

    public void broadcastResourceUpdate(String disasterId, Object payload) {
        try {
            String message = objectMapper.writeValueAsString(payload);
            messagingTemplate.convertAndSend("/topic/disasters/" + disasterId + "/resources", message);
            log.info("Broadcasted resource update for disaster: {}", disasterId);
        } catch (Exception e) {
            log.error("Error broadcasting resource update", e);
        }
    }

    public void broadcastOfficialUpdate(String disasterId, Object payload) {
        try {
            String message = objectMapper.writeValueAsString(payload);
            messagingTemplate.convertAndSend("/topic/disasters/" + disasterId + "/official-updates", message);
            log.info("Broadcasted official update for disaster: {}", disasterId);
        } catch (Exception e) {
            log.error("Error broadcasting official update", e);
        }
    }
} 