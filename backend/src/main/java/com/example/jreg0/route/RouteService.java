package com.example.jreg0.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {
    private RouteRepository _routeRepository;

    @Autowired
    public void RouteRepository(RouteRepository routeRepository) {
        this._routeRepository = routeRepository;
    }

    public List<RouteEntity> getAll(){
        return _routeRepository.findAll();
    }
}

