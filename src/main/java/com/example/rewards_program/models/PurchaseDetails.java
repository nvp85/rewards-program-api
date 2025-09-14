package com.example.rewards_program.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Data
@Table(name = "purchase_details")
public class PurchaseDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "purchase_id", nullable = false)
    private Purchase purchase;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(
            nullable = false,
            precision = 19,
            scale = 4
    )
    private BigDecimal quantity;

    @Transient
    public BigDecimal getTotalPrice() {
        return product.getPrice().multiply(quantity).setScale(2, RoundingMode.HALF_UP);
    }
}
