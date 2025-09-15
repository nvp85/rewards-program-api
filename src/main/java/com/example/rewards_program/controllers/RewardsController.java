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

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

@RestController
@RequestMapping("/api/rewards")
@RequiredArgsConstructor
public class RewardsController {

    private final PurchaseService purchaseService;

// example requests:
// http://localhost:8080/api/rewards/summary
// http://localhost:8080/api/rewards/summary?fromDate=2025-06-01&toDate=2025-09-30
    @GetMapping("/summary")
    public ResponseEntity<?> getSummary(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(required = false, defaultValue = "America/Chicago") String timeZone
            )
    {
        if ((fromDate != null && toDate != null) && (fromDate.isAfter(toDate) || fromDate.isEqual(toDate))) {
            return ResponseEntity.badRequest().body("'fromDate' must be before 'toDate'");
        }
        ZoneId zoneId = ZoneId.of(timeZone);
        ZonedDateTime fromDateTime = (fromDate != null)
                ? fromDate.atStartOfDay(zoneId)
                : ZonedDateTime.now(zoneId).minusMonths(3);
        ZonedDateTime toDateTime = (toDate != null)
                ? toDate.atTime(23, 59, 59).atZone(zoneId)
                : ZonedDateTime.now(zoneId);
        return ResponseEntity.ok().body(purchaseService.summarizeRewards(fromDateTime, toDateTime));
    }
}
