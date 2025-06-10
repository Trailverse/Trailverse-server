package org.example.trailverse.route.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hiking_paths")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HikingPath {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "path_id")
    private Long pathId;

    @Column(name = "latitude", nullable = false)
    private Double latitude; // BigDecimal (java math)

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "sequence_order", nullable = false)
    @Builder.Default
    private Integer sequenceOrder = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private HikingSession hikingSession;

    public void setHikingSession(HikingSession hikingSession) {
        this.hikingSession = hikingSession;
    }

    private HikingPath(Double latitude, Double longitude, Integer sequenceOrder) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.sequenceOrder = sequenceOrder != null ? sequenceOrder : 0;
    }

    public static HikingPath toEntity(Double latitude, Double longitude ){
        return new HikingPath(latitude, longitude,0);
    }

    public void upSequenceOrder(Integer before) {
        this.sequenceOrder = before + 1;
    }

    public Long getPathId() {
        return pathId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Integer getSequenceOrder() {
        return sequenceOrder;
    }

    public HikingSession getHikingSession() {
        return hikingSession;
    }
}