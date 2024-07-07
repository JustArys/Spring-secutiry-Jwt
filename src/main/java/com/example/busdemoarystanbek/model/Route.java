package com.example.busdemoarystanbek.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnore;

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
    private Long id;

    @Column(name = "route_from", nullable = false)
    private String routeFrom;

    @Column(name = "route_to", nullable = false)
    private String routeTo;

    @Column(name = "distance", nullable = false)
    private Integer distance;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "route")
    private List<Bus> bus = new ArrayList<>();
}
