package org.example.trailverse.route.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotNull;
//import org.antlr.v4.runtime.misc.NotNull;
import org.example.trailverse.route.repository.HikingSessionRepository;

import java.time.LocalDateTime;
import java.util.List;

//// 추가
@Getter
@NoArgsConstructor
//@AllArgsConstructor

@Builder

public class HikingSessionRequestDto {

    @NotNull(message = "사용자 ID는 필수입니다")
    @JsonProperty("userId")
    private String userId;

    @NotNull(message = "시작 시간은 필수입니다")
    @JsonProperty("startTime")
    private LocalDateTime startTime;

    @NotNull(message = "종료 시간은 필수입니다")
    @JsonProperty("endTime")
    private LocalDateTime endTime;

    @NotNull(message = "총 거리는 필수입니다")
    @DecimalMin(value = "0.0", message = "거리는 0 이상이어야 합니다")
    @JsonProperty("totalDistance")
    private Double totalDistance;

    @NotEmpty(message = "경로 데이터는 필수입니다")
    @Valid
    @JsonProperty("path")
    private List<PathPointDto> path;

    public String getUserId() {
        return this.userId;
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

    public List<PathPointDto> getPath() {
        return path;
    }

    public HikingSessionRequestDto(String userId, LocalDateTime startTime, LocalDateTime endTime, Double totalDistance, List<PathPointDto> path) {
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalDistance = totalDistance;
        this.path = path;
    }

    public static HikingSessionRequestDto of(String userId, LocalDateTime startTime, LocalDateTime endTime, Double totalDistance, List<PathPointDto> path) {
        return new HikingSessionRequestDto(
                userId, startTime, endTime, totalDistance, path);
    }
}