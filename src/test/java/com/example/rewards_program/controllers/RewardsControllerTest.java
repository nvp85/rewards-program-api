package com.example.rewards_program.controllers;

import com.example.rewards_program.services.PurchaseService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RewardsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PurchaseService purchaseService;

    @Test
    void getSummary() throws Exception {
        mockMvc.perform(get("/api/rewards/summary")
                        .param("fromDate", "2025-06-01")
                        .param("toDate", "2025-09-30")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        ArgumentCaptor <ZonedDateTime> fromCaptor = ArgumentCaptor.forClass(ZonedDateTime.class);
        ArgumentCaptor <ZonedDateTime> toCaptor = ArgumentCaptor.forClass(ZonedDateTime.class);
        Mockito.verify(purchaseService).summarizeRewards(fromCaptor.capture(), toCaptor.capture());
        ZonedDateTime fromDate = fromCaptor.getValue();
        ZonedDateTime toDate = toCaptor.getValue();
        assertEquals(ZonedDateTime.parse("2025-06-01T00:00:00-05:00[America/Chicago]"), fromDate);
        assertEquals(ZonedDateTime.parse("2025-09-30T23:59:59-05:00[America/Chicago]"), toDate);
    }

    @Test
    void getSummaryInvalidDates() throws Exception {
        String expectedError = "'fromDate' must be before 'toDate'";
        mockMvc.perform(get("/api/rewards/summary")
                        .param("fromDate", "2025-10-01")
                        .param("toDate", "2025-09-30")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedError));
    }

    @Test
    void getSummaryNoFromDate() throws Exception {
        mockMvc.perform(get("/api/rewards/summary")
                        .param("toDate", "2025-06-30")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        ArgumentCaptor <ZonedDateTime> fromCaptor = ArgumentCaptor.forClass(ZonedDateTime.class);
        ZonedDateTime to = ZonedDateTime.parse("2025-06-30T23:59:59-05:00[America/Chicago]");
        Mockito.verify(purchaseService).summarizeRewards(fromCaptor.capture(), eq(to));
        ZonedDateTime fromDate = fromCaptor.getValue();
        assertEquals(ZonedDateTime.parse(
                "2025-03-30T23:59:59-05:00[America/Chicago]"), fromDate);
    }
}