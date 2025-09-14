package com.example.rewards_program.repositories;

import com.example.rewards_program.models.RewardsRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

public interface RewardsRuleRepository extends JpaRepository<RewardsRule, Long> {
    @Query("""
        SELECT rr FROM RewardRule rr
        WHERE (rr.startDate <= :to)
          AND (rr.endDate IS NULL OR rr.endDate >= :from)
        """)
    List<RewardsRule> findActive(@Param("from") ZonedDateTime from, @Param("to") ZonedDateTime to);
}
