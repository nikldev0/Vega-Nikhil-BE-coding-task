package com.vega.be_coding_task_nikhil.model.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.vega.be_coding_task_nikhil.model.enums.InvestorType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record OnboardingFlowDTO(
        UUID flowId,

        @NotNull(message = "Fund ID is required")
        UUID fundId,

        @NotNull(message = "Investor type is required")
        InvestorType investorType,

        @NotNull(message = "Task IDs list cannot be null")
        @Size(min = 1, message = "At least one task ID is required")
        List<UUID> taskIds,

        @NotNull(message = "Minimum investment amount is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Minimum investment amount must be greater than zero")
        BigDecimal minimumInvestment
) {}