package com.example.busdemoarystanbek.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.time.LocalDateTime;

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

    @JsonIgnore
    @Column(name = "availabel_seats", nullable = false)
    private Integer availableSeats;

    @Column(name = "seats", nullable = false)
    private Integer seats;

    @Column(name = "plate_number", nullable = false)
    private String plate;





}
