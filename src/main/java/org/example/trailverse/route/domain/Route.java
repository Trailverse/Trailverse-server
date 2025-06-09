package org.example.trailverse.route.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.trailverse.user.domain.User;
@Getter
@Entity
public class Route {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;


    /*
    * fk:userid
    * 총이동거리
    * 총이동시간-이동시작시간,이동종료시간
    * 산분류
    *
    * */
}
