package com.example.busdemoarystanbek.controller;

import com.example.busdemoarystanbek.model.Reservation;
import com.example.busdemoarystanbek.service.ReservationService;
import com.example.busdemoarystanbek.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class ReservationController {
    private ReservationService reservationservice;
    private UserService userService;
    @PostMapping("/reservation/")
    public ResponseEntity<Reservation> addReservationHandler( @RequestBody Reservation reservation)  {
        reservation.setReservationDate(LocalDate.now());
        Reservation saveReservation = 	reservationservice.addReservation(reservation, userService.getAuthenticatedUser());

        return new ResponseEntity<Reservation>(saveReservation, HttpStatus.CREATED);

    }

    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<Reservation>	getReservationByreservationIdHandler(@PathVariable("reservationId") Long reservationId){

        Reservation reservation = reservationservice.viewReservation(reservationId);

        return new ResponseEntity<Reservation>(reservation,HttpStatus.OK);
    }


    @GetMapping("reservations")
    public ResponseEntity<List<Reservation>> viewAllReservationHandler() {

        List<Reservation> reservations = reservationservice.viewAllReservation();

        return new ResponseEntity<List<Reservation>>(reservations,HttpStatus.OK);
    }


    @DeleteMapping("/reservation/{reservationId}")
    public ResponseEntity<Reservation> deleteReservationByRollHandler( @PathVariable("reservationId") Long reservationId) {

        Reservation reservation= reservationservice.deleteReservation(reservationId, userService.getAuthenticatedUser());

        return new ResponseEntity<Reservation>(reservation,HttpStatus.OK);

    }

    @PutMapping("/reservation")
    public ResponseEntity<Reservation> updateReservationHandler(@RequestBody Reservation reservation) {
        reservation.setReservationDate(LocalDate.now());
        Reservation updatedReservation= reservationservice.updateReservation(reservation, userService.getAuthenticatedUser());
        return new ResponseEntity<Reservation>(updatedReservation,HttpStatus.OK);

    }
}
