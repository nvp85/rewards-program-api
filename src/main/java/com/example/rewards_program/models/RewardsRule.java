package com.example.rewards_program.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Integer pointsPerDollar;
    private Long minimumTotalPurchase;
    private Long maximumTotalPurchase = Long.MAX_VALUE;
    @Column(nullable = false)
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
}
