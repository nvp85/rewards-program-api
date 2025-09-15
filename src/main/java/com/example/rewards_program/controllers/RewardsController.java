package com.example.rewards_program.controllers;

import com.example.rewards_program.DTOs.RewardsSummaryDTO;
import com.example.rewards_program.services.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/rewards")
@RequiredArgsConstructor
public class RewardsController {

    private final PurchaseService purchaseService;

    @GetMapping("/summary")
    public ResponseEntity<RewardsSummaryDTO> getSummary(
            @RequestParam(defaultValue = "2025-06-01T00:00:00Z") String fromDateStr,
            @RequestParam(defaultValue = "2025-09-30T23:59:59Z") String toDateStr
    ) {
        // should return rewards summary
        ZonedDateTime fromDate = ZonedDateTime.parse(fromDateStr);
        ZonedDateTime toDate = ZonedDateTime.parse(toDateStr);

        return ResponseEntity.ok().body(purchaseService.summarizeRewards(fromDate, toDate));
    }
}
