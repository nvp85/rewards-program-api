package com.example.rewards_program.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.List;

// Represents a purchase made by a customer
// Each transaction can include many items (products) in different quantities
@Entity
@NoArgsConstructor
@Data
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    private ZonedDateTime purchaseDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "purchase")
    private List<PurchaseDetails> purchaseDetailsList;
    // total point earned for this purchase
    @Transient
    private Long totalRewardPoints;

    @Transient
    public BigDecimal getTotalDollars(){
        return this.purchaseDetailsList.stream()
                .map(PurchaseDetails::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

}

