package com.example.rewards_program.services;

import com.example.rewards_program.models.Purchase;
import com.example.rewards_program.models.RewardsRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RewardsRuleServiceTest {

    private final RewardsRuleService service = new RewardsRuleService();

    private static RewardsRule rule1;
    private static RewardsRule rule2;

    @BeforeAll
    static void setup() {
        rule1 = new RewardsRule();
        rule1.setStartDate(ZonedDateTime.parse("2025-01-01T00:00:00Z"));
        rule1.setEndDate(null);
        rule1.setMinimumTotalPurchase(50L);
        rule1.setMaximumTotalPurchase(100L);
        rule1.setPointsPerDollar(1);

        rule2 = new RewardsRule();
        rule2.setStartDate(ZonedDateTime.parse("2025-01-01T00:00:00Z"));
        rule2.setEndDate(null);
        rule2.setMinimumTotalPurchase(100L);
        rule2.setPointsPerDollar(2);
    }

    @Test
    void calculateRewardPoints() {
        Purchase purchase = Mockito.mock(Purchase.class);
        when(purchase.getPurchaseDate()).thenReturn(java.time.ZonedDateTime.now());
        when(purchase.getTotalDollars()).thenReturn(BigDecimal.valueOf(120.67));

        Long points = service.calculateRewardPoints(rule1, purchase);
        assertEquals(50L, points);

        points = service.calculateRewardPoints(rule2, purchase);
        assertEquals(40L, points);

    }
}