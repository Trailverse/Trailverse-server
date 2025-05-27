package org.example.trailverse.route.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RouteGPS {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
