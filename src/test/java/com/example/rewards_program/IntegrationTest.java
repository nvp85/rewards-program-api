package com.example.rewards_program;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Test the full stack including the database (H2 in-memory)
// The data is loaded from src/main/resources/data.sql
@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getSummary() throws Exception {
        // based on the /resources/data.sql file
        String expectedJson = """
                {
                    "fromDate": "2025-06-01T00:00:00Z",
                    "toDate": "2025-09-30T23:59:59Z",
                    "customers": [
                        {
                            "customerId": 1,
                            "customerName": "Alice",
                            "totalPoints": 380,
                            "pointsPerMonth": [
                                {"month": "2025-07", "points": 224},
                                {"month": "2025-08", "points": 74},
                                {"month": "2025-09", "points": 82}
                            ]
                        },
                        {
                            "customerId": 2,
                            "customerName": "Bob",
                            "totalPoints": 314,
                            "pointsPerMonth": [
                                {"month": "2025-07", "points": 0},
                                {"month": "2025-08", "points": 192},
                                {"month": "2025-09", "points": 122}
                            ]
                        }
                    ]
                }
                """;
        mockMvc.perform(get("/api/rewards/summary").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }
}