package com.example.busdemoarystanbek.service;

import com.example.busdemoarystanbek.model.Bus;
import com.example.busdemoarystanbek.model.Route;
import com.example.busdemoarystanbek.repository.BusRepository;
import com.example.busdemoarystanbek.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RouteService {
    private final BusRepository busRepository;
    private final RouteRepository routeRepository;

    public Route addRoute(Route route){
        return routeRepository.save(route);
    }
    public Route updateRoute(Route route,Route updateRoute){
        updateRoute.setRouteTo(route.getRouteTo());
        updateRoute.setRouteFrom(route.getRouteFrom());
        updateRoute.setDistance(route.getDistance());
        updateRoute.setDepartureTime(route.getDepartureTime());
        updateRoute.setArrivalTime(route.getArrivalTime());
        return routeRepository.save(updateRoute);
    }

    public Route deleteRoute(Long id) {
        Route route = routeRepository.findById(id).get();
         routeRepository.deleteById(id);
         return route;
    }

    public void assignBusToRoute(Long routeId, Long busId){
        Route route = routeRepository.findById(routeId).orElseThrow();
        Bus bus = busRepository.findById(busId).orElseThrow();
        route.setBus(bus);
        routeRepository.save(route);
    }

    public Route viewRoute(Long id){
        return findRouteById(id);
    }

    public List<Route> viewAllRoute(){
        return routeRepository.findAll();
    }

    public Route findRouteById(Long id){
        return routeRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(String.format("No such route with id '%d' not found", id)));
    }

}
