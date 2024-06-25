package com.vega.be_coding_task_nikhil.model.dto;

import java.time.Instant;
import java.util.UUID;

import com.vega.be_coding_task_nikhil.model.enums.SubscriptionStatus;
import jakarta.validation.constraints.NotNull;

public record SubscriptionDTO(
        UUID subscriptionId,

        @NotNull(message = "Investor ID is required")
        UUID investorId,

        @NotNull(message = "Fund ID is required")
        UUID fundId,

        @NotNull(message = "Subscription status is required")
        SubscriptionStatus status,

        @NotNull(message = "Created at timestamp is required")
        Instant createdAt,

        @NotNull(message = "Last updated timestamp is required")
        Instant lastUpdated
) {}
