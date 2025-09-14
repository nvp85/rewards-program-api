package com.example.rewards_program.controllers;

import com.example.rewards_program.DTOs.RewardsSummaryDTO;
import com.example.rewards_program.services.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/rewards")
@RequiredArgsConstructor
public class Controller {

    private final PurchaseService purchaseService;

    @GetMapping("/summary")
    public ResponseEntity<RewardsSummaryDTO> getSummary() {
        // should return rewards summary
        String summaryJson ="""
                [
                  {
                    "customerId": 101,
                    "customerName": "Alice",
                    "months": {"2025-06": 120, "2025-07": 80, "2025-08": 200},
                    "total": 400
                  },
                  {
                    "customerId": 202,
                    "customerName": "Bob",
                    "months": {"2025-06": 0, "2025-07": 75, "2025-08": 40},
                    "total": 115
                  }
                ]
                """;
        ZonedDateTime fromDate = ZonedDateTime.parse("2025-06-01T00:00:00Z");
        ZonedDateTime toDate = ZonedDateTime.parse("2025-08-31T23:59:59Z");

        return ResponseEntity.ok().body(purchaseService.summarizeRewards(fromDate, toDate));
    }
}
