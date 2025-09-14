package com.example.rewards_program.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRewardsDTO {
    private Long customerId;
    private String customerName;
    private Integer totalPoints;
    private List<PointsPerMonthDTO> pointsPerMonth;

}
