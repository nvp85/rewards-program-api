package com.example.rewards_program.repositories;

import com.example.rewards_program.models.Purchase;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    // Fetch associated customer and purchase details with products to avoid n+1 problem
    @EntityGraph(attributePaths = {"customer", "purchaseDetails", "purchaseDetails.product"})
    List<Purchase> findByPurchaseDateBetween(ZonedDateTime from, ZonedDateTime to);

}
