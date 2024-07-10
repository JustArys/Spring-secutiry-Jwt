package com.example.busdemoarystanbek.controller;

import com.example.busdemoarystanbek.model.Bus;
import com.example.busdemoarystanbek.model.request.BusRequest;
import com.example.busdemoarystanbek.service.BusService;
import com.example.busdemoarystanbek.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bus")
@RequiredArgsConstructor
public class BusController {
    private final BusService busService;
    private final UserService userService;

    @PostMapping("/addBus")
    public ResponseEntity<Bus> createBus(@RequestBody BusRequest bus){
        if (userService.isAdmin(userService.getAuthenticatedUser())){
        return new ResponseEntity<>(busService.addBus(bus), HttpStatus.CREATED);}
        else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bus> updateBus(@PathVariable("id") Long id, @RequestBody BusRequest bus){
        if(userService.isAdmin(userService.getAuthenticatedUser())){
        return new ResponseEntity<>(busService.updateBus(bus, busService.viewBus(id)), HttpStatus.ACCEPTED);}
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/deleteBus/{id}")
    public ResponseEntity<Bus> deleteBus(@PathVariable("id") Long id){
        if(userService.isAdmin(userService.getAuthenticatedUser())){
        return new ResponseEntity<>(busService.deleteBus(id), HttpStatus.OK);}
        else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
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
    public ResponseEntity<List<Bus>> viewItByType(@PathVariable("busType") String type){
        return new ResponseEntity<>(busService.viewBusByType(type), HttpStatus.FOUND);
    }

}
