package com.example.busdemoarystanbek.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    private String field1;
    private int field2;
    // добавьте остальные поля
}
