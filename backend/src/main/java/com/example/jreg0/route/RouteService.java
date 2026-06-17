package com.example.jreg0.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {
    private final RouteRepository _routeRepository;

    @Autowired
    public RouteService(RouteRepository routeRepository) {
        this._routeRepository = routeRepository;
    }

    public List<RouteEntity> getAll(){
        return _routeRepository.findAll();
    }
}

