package com.example.rewards_program.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RewardsRule {
    private Long id;
    private String description;
    private Integer pointsPerDollar;
    private Double minimumTotalPurchase;
    private Double maximumTotalPurchase;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
}
