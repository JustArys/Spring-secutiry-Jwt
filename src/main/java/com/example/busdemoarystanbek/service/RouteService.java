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
    private BusRepository busRepository;
    private RouteRepository routeRepository;

    public Route addRoute(Route route){
        Route r = routeRepository.findByRouteFromAndRouteTo(route.getRouteFrom(), route.getRouteTo());
        if(r == null){
            List<Bus> busList = route.getBus();
            for (Bus bus : busList) {
                bus.setRouteFrom(route.getRouteFrom());
                bus.setRouteTo(route.getRouteTo());
                bus.setRoute(route);
                busRepository.save(bus);
            }
            return routeRepository.save(route);
        }
        else {
            throw new RuntimeException("route already exists");
        }
    }
    public Route updateRoute(Route route){
        Optional<Route> opt = routeRepository.findById(route.getId());
        if(opt.isPresent()){
            List<Bus> busList = opt.get().getBus();

            for(Bus b: busList) {
                b.setRouteFrom(route.getRouteFrom());
                b.setRouteTo(route.getRouteTo());
            }
            return routeRepository.save(route);
        }
        else {
            throw new RuntimeException("No such route present to update");
        }
    }

    public Route deleteRoute(Long id) {
        Route route = findRouteById(id);
        routeRepository.delete(route);
        return route;
    }

    public Route viewRoute(Long id){
        return findRouteById(id);
    }

    public List<Route> viewAllRoute(){
        List<Route> list = routeRepository.findAll();
        if(!list.isEmpty()){
            return list;
        }
        else {
            throw new RuntimeException("No routes present");
        }
    }

    public Route findRouteById(Long id){
        return routeRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(String.format("No such route with id '%d' not found", id)));
    }

}
