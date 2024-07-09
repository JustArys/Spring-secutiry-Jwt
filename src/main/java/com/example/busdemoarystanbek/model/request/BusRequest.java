package com.example.busdemoarystanbek.model.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusRequest {
    private String busName;
    private String driverName;
    private String busType;
    private Integer seats;
    private String plate;
}
