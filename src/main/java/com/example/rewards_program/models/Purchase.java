package com.example.rewards_program.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    private ZonedDateTime purchaseDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "purchase")
    private List<PurchaseDetails> purchaseDetailsList;
    // total point earned for this purchase
    // they are calculated and saved for convenience of querying
    //private Integer totalRewardPoints;

    @Transient
    private BigDecimal getTotalDollars(){
        return this.purchaseDetailsList.stream()
                .map(PurchaseDetails::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

}

