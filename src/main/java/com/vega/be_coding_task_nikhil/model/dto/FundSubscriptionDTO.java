package com.vega.be_coding_task_nikhil.model.dto;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record FundSubscriptionDTO(
        @NotNull(message = "Fund ID is required")
        UUID fundId,

        @NotEmpty(message = "Fund name is required")
        String fundName,

        @NotNull(message = "Total investment volume is required")
        @Positive(message = "Total investment volume must be positive")
        BigDecimal totalInvestmentVolume,

        @NotNull(message = "Active subscriptions count is required")
        @Positive(message = "Active subscriptions count must be positive")
        long activeSubscriptionsCount
) {}
