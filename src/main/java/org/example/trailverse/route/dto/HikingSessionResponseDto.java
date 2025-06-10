package org.example.trailverse.route.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class HikingSessionResponseDto {

    private Long sessionId;
    private String userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double totalDistance;
    private LocalDateTime createdAt;
    private String message;

    public Long getSessionId() {
        return sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Double getTotalDistance() {
        return totalDistance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getMessage() {
        return message;
    }

    public HikingSessionResponseDto(Long sessionId, String userId, LocalDateTime startTime, LocalDateTime endTime, Double totalDistance, LocalDateTime createdAt, String message) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalDistance = totalDistance;
        this.createdAt = createdAt;
        this.message = message;
    }
}