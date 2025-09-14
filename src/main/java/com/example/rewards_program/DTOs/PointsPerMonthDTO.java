package com.example.rewards_program.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointsPerMonthDTO {
    private String month;
    private Integer points;
}
