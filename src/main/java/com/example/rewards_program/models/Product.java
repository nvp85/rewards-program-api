package com.example.rewards_program.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    // price per unit
    @Column(precision = 10, scale = 2)
    private BigDecimal price;
    // unit of measurement (piece, kg, liter, etc.)
    private String unit;
}
