package com.example.rewards_program.DTOs;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class RewardsSummaryDTO {
    private ZonedDateTime fromDate;
    private ZonedDateTime toDate;
    private List<CustomerRewardsDTO> customers;
}
