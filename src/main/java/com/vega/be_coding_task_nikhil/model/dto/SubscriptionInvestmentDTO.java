package com.vega.be_coding_task_nikhil.model.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.vega.be_coding_task_nikhil.model.enums.SubscriptionStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SubscriptionInvestmentDTO(
        @NotNull(message = "Subscription ID is required")
        UUID subscriptionId,

        @NotNull(message = "Investor ID is required")
        UUID investorId,

        @NotNull(message = "Fund ID is required")
        UUID fundId,

        @NotEmpty(message = "Fund name is required")
        String fundName,

        @NotNull(message = "Investment volume is required")
        @Positive(message = "Investment volume must be positive")
        BigDecimal investmentVolume,

        @NotNull(message = "Subscription status is required")
        SubscriptionStatus status
) {}
