package com.example.busdemoarystanbek.controller;

import com.example.busdemoarystanbek.model.Bus;
import com.example.busdemoarystanbek.model.request.BusRequest;
import com.example.busdemoarystanbek.service.BusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bus")
@RequiredArgsConstructor
public class BusController {
    private BusService busService;

    @PostMapping("/addBus")
    public ResponseEntity<Bus> createBus(@RequestBody BusRequest bus){
        return new ResponseEntity<>(busService.addBus(bus), HttpStatus.CREATED);
    }

    @PutMapping("/updateBus")
    public ResponseEntity<Bus> updateBus(@RequestBody Bus bus){
        return new ResponseEntity<>(busService.updateBus(bus), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteBus/{id}")
    public ResponseEntity<Bus> deleteBus(@PathVariable("id") Long id){
        return new ResponseEntity<>(busService.deleteBus(id), HttpStatus.OK);
    }

    @GetMapping("/bus/{id}")
    public ResponseEntity<Bus> viewBusById(@PathVariable("id") Long id){
        return new ResponseEntity<>(busService.viewBus(id), HttpStatus.FOUND);
    }
    @GetMapping("/buses")
    public ResponseEntity<List<Bus>> viewAll(){
        return new ResponseEntity<>(busService.viewAllBus(),HttpStatus.OK);
    }

    @GetMapping("busByType/{busType}")
    public ResponseEntity<List<Bus>> viewItbyType(@PathVariable("busType") String type){
        return new ResponseEntity<>(busService.viewBusByType(type), HttpStatus.FOUND);
    }


}
