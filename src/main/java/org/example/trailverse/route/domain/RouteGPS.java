package org.example.trailverse.route.domain;

import jakarta.persistence.*;

@Entity
public class RouteGPS {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String startTime;
    private String endTime;
    private double totalDistance;
    private Long path;


    @ManyToOne
    private Route route;
}
