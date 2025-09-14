package com.example.rewards_program.services;

import com.example.rewards_program.models.Purchase;
import com.example.rewards_program.models.RewardsRule;
import org.springframework.stereotype.Service;

@Service
public class RewardsRuleService {

    public boolean isApplicable(RewardsRule rule, Purchase purchase) {
        return (rule.getEndDate() == null || purchase.getPurchaseDate().isBefore(rule.getEndDate()))
                && purchase.getPurchaseDate().isAfter(rule.getStartDate());
    }

    public Long calculateRewardPoints(RewardsRule rule, Purchase purchase) {
        if (!isApplicable(rule, purchase)) {
            return 0L;
        }
        long total = purchase.getTotalDollars().longValue();
        long minTotal = rule.getMinimumTotalPurchase();
        long maxTotal = rule.getMaximumTotalPurchase();
        long points = rule.getPointsPerDollar();
        return points * Math.max(0, Math.min(total - minTotal, maxTotal - minTotal));
    }
}
