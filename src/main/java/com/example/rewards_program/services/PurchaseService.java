package com.example.rewards_program.services;

import com.example.rewards_program.DTOs.CustomerRewardsDTO;
import com.example.rewards_program.DTOs.RewardsSummaryDTO;
import com.example.rewards_program.models.Purchase;
import com.example.rewards_program.models.RewardsRule;
import com.example.rewards_program.repositories.PurchaseRepository;
import com.example.rewards_program.repositories.RewardsRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final RewardsRuleRepository rewardsRuleRepository;
    private final PurchaseRepository purchaseRepository;
    private final RewardsRuleService rewardsRuleService;

    public RewardsSummaryDTO summarizeRewards(ZonedDateTime from, ZonedDateTime to) {
        RewardsSummaryDTO summary = new RewardsSummaryDTO();
        summary.setFromDate(from);
        summary.setToDate(to);
        summary.setCustomers(new ArrayList<CustomerRewardsDTO>());
        // Fetch purchases within the date range
        // The type of fetch is eager
        List<Purchase> purchases = purchaseRepository.findByPurchaseDateBetween(from, to);
        List<RewardsRule> activeRules = rewardsRuleRepository.findActive(from, to);

        Map<Long, Map<String, Long>> customerRewardsMap = new HashMap<>();
        // Calculate rewards based on purchases and active rules
        for (Purchase purchase : purchases) {
            purchase.setTotalRewardPoints(0L);
            for (RewardsRule rule : activeRules) {
                // Apply each rule to the purchase (if not applicable it will return 0 points)
                purchase.setTotalRewardPoints(
                        rewardsRuleService.calculateRewardPoints(rule, purchase) + purchase.getTotalRewardPoints()
                );
            }
            YearMonth month = YearMonth.from(purchase.getPurchaseDate());
            customerRewardsMap.putIfAbsent(purchase.getCustomer().getId(), new HashMap<>());
            Map<String, Long> monthlyPoints = customerRewardsMap.get(purchase.getCustomer().getId());
            monthlyPoints.put(month.toString(),
                    monthlyPoints.getOrDefault(month.toString(), 0L) + purchase.getTotalRewardPoints());
            monthlyPoints.put("total",
                    monthlyPoints.getOrDefault("total", 0L) + purchase.getTotalRewardPoints());
        }


        return summary;
    }
}
