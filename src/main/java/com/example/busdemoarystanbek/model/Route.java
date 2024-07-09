package com.example.busdemoarystanbek.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Route {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "route_id")
    private Long id;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "arrival_time")

    private LocalDateTime arrivalTime;

    @Column(name = "route_from", nullable = false)
    private String routeFrom;

    @Column(name = "route_to", nullable = false)
    private String routeTo;

    @Column(name = "distance", nullable = false)
    private Integer distance;


    @JsonIgnore
    @ManyToOne
    private Bus bus;


}
