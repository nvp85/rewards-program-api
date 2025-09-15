package com.example.rewards_program.controllers;

import com.example.rewards_program.DTOs.RewardsSummaryDTO;
import com.example.rewards_program.services.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
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

// example requests:
// http://localhost:8080/api/rewards/summary
// http://localhost:8080/api/rewards/summary?fromDate=2025-06-01T01:30:00.000-05:00&toDate=2025-09-30T01:30:00.000-05:00
    @GetMapping("/summary")
    public ResponseEntity<?> getSummary(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) ZonedDateTime fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) ZonedDateTime toDate
    ) {
        if (fromDate == null) {
            fromDate = ZonedDateTime.now().minusMonths(3);
        }
        if (toDate == null) {
            toDate = ZonedDateTime.now();
        }
        if (fromDate.isAfter(toDate) || fromDate.isEqual(toDate)) {
            return ResponseEntity.badRequest().body("'fromDate' must be before 'toDate'");
        }
        return ResponseEntity.ok().body(purchaseService.summarizeRewards(fromDate, toDate));
    }
}
