package org.example.trailverse.route.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.trailverse.user.domain.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hiking_sessions")
@Getter
public class HikingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Long sessionId;

    @Column(name = "user_id", nullable = false, length = 100)
    private String userId;
//    @ManyToOne
//    private User user;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "total_distance", nullable = false)
    private Double totalDistance;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "hikingSession", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<HikingPath> paths = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    private HikingSession(String userId, LocalDateTime startTime, LocalDateTime endTime, Double totalDistance) {
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalDistance = totalDistance;
    }

    public static HikingSession toEntity(String userId, LocalDateTime startTime, LocalDateTime endTime, Double totalDistance) {
        return new HikingSession(userId, startTime, endTime, totalDistance);
    }


    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(Double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<HikingPath> getPaths() {
        return paths;
    }

    public void setPaths(List<HikingPath> paths) {
        this.paths = paths;
    }

    // 연관관계 편의 메서드
    public void addPath(HikingPath path) {
        paths.add(path);
        path.setHikingSession(this);
    }
}