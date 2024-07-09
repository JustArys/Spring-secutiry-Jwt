package com.example.busdemoarystanbek.service;

import com.example.busdemoarystanbek.model.Bus;
import com.example.busdemoarystanbek.model.request.BusRequest;
import com.example.busdemoarystanbek.repository.BusRepository;
import com.example.busdemoarystanbek.repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusService {
    private final BusRepository busRepository;

    public BusService(BusRepository busRepository){
        this.busRepository = busRepository;
    }

    public Bus addBus(BusRequest request){
        var bus = Bus.builder().busName(request.getBusName())
                .busType(request.getBusType())
                .seats(request.getSeats())
                .availableSeats(request.getSeats())
                .plate(request.getPlate())
                .driverName(request.getDriverName())
                .build();
        busRepository.save(bus);
        return bus;
    }

    public Bus updateBus(BusRequest request, Bus bus){
        bus.setSeats(request.getSeats());
        bus.setBusName(request.getBusName());
        bus.setDriverName(request.getDriverName());
        bus.setBusType(request.getBusType());
        bus.setPlate(request.getPlate());
        bus.setAvailableSeats(request.getSeats());
        busRepository.save(bus);
        return bus;
    }
    public Bus deleteBus(Long id){
        Bus bus = viewBus(id);
        busRepository.delete(bus);
        return bus;
    }

    public Bus viewBus(Long id){
        Optional<Bus> opt = busRepository.findById(id);
        if(opt.isPresent()){
            return opt.get();
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
