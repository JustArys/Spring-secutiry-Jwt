package com.example.busdemoarystanbek.service;

import com.example.busdemoarystanbek.model.Bus;
import com.example.busdemoarystanbek.model.Route;
import com.example.busdemoarystanbek.model.request.BusRequest;
import com.example.busdemoarystanbek.repository.BusRepository;
import com.example.busdemoarystanbek.repository.RouteRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusService {
    private final BusRepository busRepository;
    private final RouteRepository routeRepository;

    public BusService(BusRepository busRepository, RouteRepository routeRepository){
        this.busRepository = busRepository;
        this.routeRepository = routeRepository;
    }

    public Bus addBus(BusRequest request){
        Bus b = busRepository.findByDriverName(request.getDriverName());
        Bus buss = null;
        if(b != null) {
            Route r = routeRepository.findByRouteFromAndRouteTo(request.getRouteFrom(), request.getRouteTo());
            if (r != null) {
                buss = Bus.builder().busName(request.getBusName())
                        .busType(request.getBusType())
                        .driverName(request.getDriverName())
                        .routeFrom(request.getRouteFrom())
                        .routeTo(request.getRouteTo())
                        .departureTime(request.getDepartureTime())
                        .arrivalTime(request.getArrivalTime())
                        .availableSeats(request.getAvailableSeats())
                        .seats(request.getSeats())
                        .plate(request.getPlate())
                        .build();
                buss.setRoute(r);

            }
            return busRepository.save(buss);
        }

        else {
            throw new RuntimeException("Bus already exists with given driver name");
        }

    }

    public Bus updateBus(Bus bus){
        Optional<Bus> optBus = busRepository.findById(bus.getId());
        if(optBus.isPresent()){
            Bus existingBus = optBus.get();
            Route r = routeRepository.findByRouteFromAndRouteTo(existingBus.getRouteFrom(), existingBus.getRouteTo());
            if(r!=null){
                List<Bus> list = r.getBus();
                list.add(bus);
                existingBus.setRoute(r);
            }
            else {
                throw new RuntimeException("No such route found");
            }
            busRepository.save(existingBus);
            return existingBus;
        }
        else {
            throw new RuntimeException("No Bus found with given details");
        }
    }
    public Bus deleteBus(Long id){
        Optional<Bus> opt = busRepository.findById(id);
        if(opt.isPresent()){
            Bus b = opt.get();
            b.setRoute(null);
            busRepository.delete(b);
            return b;
        }
        else {
            throw new RuntimeException(STR."No Bus present with given id :\{id}");
        }
    }

    public Bus viewBus(Long id){
        Optional<Bus> opt = busRepository.findById(id);
        if(opt.isPresent()){
            Bus b = opt.get();
            return b;
        }
        else {
            throw new RuntimeException(STR."No Bus present with given id :\{id}");
        }
    }

    public List<Bus> viewBusByType(String busType) {
        List<Bus> b = busRepository.findByBusType(busType);
        if(b.size()>0){
            return b;
        } else {
            throw new RuntimeException(STR."No Bus present with given type :\{busType}");
        }
    }
    public List<Bus> viewAllBus() {
        return busRepository.findAll();
    }

}
