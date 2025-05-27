package org.example.trailverse.route.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Route {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    * fk:userid
    * 총이동거리
    * 총이동시간-이동시작시간,이동종료시간
    * 산분류
    *
    * */
}
