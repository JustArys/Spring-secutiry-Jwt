package com.example.busdemoarystanbek.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="Bus")
public class Bus {

    @JsonIgnore
    @Id
    @Column(name = "bus_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "bus_name", nullable = false)
    private String busName;

    @Column(name = "driver_name", nullable = false)
    private String driverName;

    @Column(name = "bus_type", nullable = false)
    private String busType;

    @Column(name = "route_from", nullable = false)
    private String routeFrom;
    @Column(name = "route_to", nullable = false)
    private String routeTo;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @Column(name = "availabel_seats", nullable = false)
    private Integer availableSeats;
    @Column(name = "seats", nullable = false)
    private Integer seats;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "route_FK")
    @JsonIgnore
    private Route route;

    @Column(name = "plate_number", nullable = false)
    private String plate;





}
