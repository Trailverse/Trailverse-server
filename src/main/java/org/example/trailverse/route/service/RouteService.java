package org.example.trailverse.route.service;

import lombok.RequiredArgsConstructor;
import org.example.trailverse.route.domain.Route;
import org.example.trailverse.route.repository.RouteRepository;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class RouteService {
    private final RouteRepository routeRepository;


    public Route findById(Long routeId){
        return routeRepository.findById(routeId).orElseThrow(()->new NullPointerException("찾는 길의 아이디는 없습니다."));

    }
}
