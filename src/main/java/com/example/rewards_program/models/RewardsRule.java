package com.example.rewards_program.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

// Represents a rule for earning reward points
// For flexibility, rules are stored in the database
@Entity
@Data
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
