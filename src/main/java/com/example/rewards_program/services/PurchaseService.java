package com.example.rewards_program.services;

import com.example.rewards_program.DTOs.CustomerRewardsDTO;
import com.example.rewards_program.DTOs.PointsPerMonthDTO;
import com.example.rewards_program.DTOs.RewardsSummaryDTO;
import com.example.rewards_program.models.Purchase;
import com.example.rewards_program.models.RewardsRule;
import com.example.rewards_program.repositories.PurchaseRepository;
import com.example.rewards_program.repositories.RewardsRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.*;

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
        // Fetch active reward rules within the date range
        List<RewardsRule> activeRules = rewardsRuleRepository.findActive(from, to);
        // Accumulate rewards per customer per month, total, and customer names in maps
        Map<Long, Map<String, Long>> customerRewardsMap = new HashMap<>();
        Map<Long, String> customerNamesMap = new HashMap<>();
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
            customerNamesMap.put(purchase.getCustomer().getId(), purchase.getCustomer().getName());
        }
        // construct the summary DTO
        List<CustomerRewardsDTO> customerRewardsList = customerRewardsMap.entrySet().stream()
                .map(entry -> {
                CustomerRewardsDTO customerRewards = new CustomerRewardsDTO();
                customerRewards.setCustomerId(entry.getKey());
                customerRewards.setCustomerName(customerNamesMap.get(entry.getKey()));
                List<PointsPerMonthDTO> pointsPerMonth = new ArrayList<>();
                entry.getValue().entrySet().stream()
                        .filter(e -> !e.getKey().equals("total"))
                        .forEach(e -> pointsPerMonth.add(new PointsPerMonthDTO(e.getKey(), e.getValue())));
                pointsPerMonth.sort(Comparator.comparing(PointsPerMonthDTO::getMonth));
                customerRewards.setPointsPerMonth(pointsPerMonth);
                customerRewards.setTotalPoints(entry.getValue().get("total"));
                return customerRewards;
        }).toList();
        summary.setCustomers(customerRewardsList);
        return summary;
    }
}
