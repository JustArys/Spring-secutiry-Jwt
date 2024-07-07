package com.example.busdemoarystanbek.service;

import com.example.busdemoarystanbek.model.Bus;
import com.example.busdemoarystanbek.model.Reservation;
import com.example.busdemoarystanbek.model.ReservationStatus;
import com.example.busdemoarystanbek.model.User;
import com.example.busdemoarystanbek.repository.BusRepository;
import com.example.busdemoarystanbek.repository.ReservationRepository;
import com.example.busdemoarystanbek.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private ReservationRepository reservationRepository;
    private UserRepository userRepository;
    private BusRepository busRepository;
    public Reservation addReservation(Reservation reservation, User user){
        Reservation r = null;

        List<Bus> busList = busRepository.findByRouteFromAndRouteTo(reservation.getSource(), reservation.getDestination());
        if(busList.isEmpty()){
            throw new RuntimeException("Sorry no buses present in that route to reserve");
        }
        else {
            for (Bus b : busList){
                if(b.getAvailableSeats() > 0){
                    b.setAvailableSeats(b.getAvailableSeats()-reservation.getReservations());

                    reservation.setBus(b);
                    reservation.setReservationStatus(ReservationStatus.BOOKED.name());
                    user.setReservation(reservation);
                    r = reservationRepository.save(reservation);
                    break;
                }
            }
        }
        if(r == null){
            throw new RuntimeException("No seats left to Book... update Failed");
        }
        return r;
    }

    public Reservation viewReservation(Long id){
        Optional<Reservation> opt = reservationRepository.findById(id);
        return opt.orElseThrow(() ->
                new NoSuchElementException(STR."No reservation with this id: \{id}"));
    }

    public List<Reservation> viewAllReservation(){
        List<Reservation> reservations = reservationRepository.findAll();
        if(reservations.isEmpty()){
            throw new RuntimeException("No reservation Found");
        }
        return reservations;
    }

    public Reservation deleteReservation(Long id, User user){
        Optional<Reservation> opt = reservationRepository.findById(id);
        if(opt.isPresent()){
            Reservation reservation = opt.get();
            Bus bus = reservation.getBus();
            bus.setAvailableSeats(bus.getAvailableSeats() + reservation.getReservations());
            reservation.setReservationStatus(ReservationStatus.CANCELED.name());
            reservation.setBus(null);
            user.setReservation(null);
            reservationRepository.delete(reservation);
            return reservation;
        }
        else {
            throw new RuntimeException("No reservation found with id");
        }
    }

    public Reservation updateReservation(Reservation reservation, User user){
        Reservation r = null;
        List<Bus> busList = busRepository.findByRouteFromAndRouteTo(reservation.getSource(), reservation.getDestination());
        if(busList.isEmpty()){
            throw new RuntimeException("No buses present");
        }
        for(Bus b: busList){
            if(b.getAvailableSeats()>0){
                Optional<Reservation> reOpt =  reservationRepository.findById(reservation.getId());
                Reservation re = reOpt.get();
                b.setAvailableSeats(b.getAvailableSeats()+ re.getReservations());
                b.setAvailableSeats(b.getAvailableSeats()-reservation.getReservations());
                reservation.setBus(b);
                reservation.setReservationStatus(ReservationStatus.BOOKED.name());
                user.setReservation(reservation);
                r = reservationRepository.save(reservation);
                break;
            }
        }
        if(r == null){
            throw new RuntimeException("No seat left to Book");
        }
        return r;
    }
}
