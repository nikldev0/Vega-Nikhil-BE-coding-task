package com.vega.be_coding_task_nikhil.model.dto;

import java.util.UUID;

import com.vega.be_coding_task_nikhil.model.enums.InvestorType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record InvestorDTO(
        UUID investorId,

        @NotNull(message = "Investor type is required")
        InvestorType type,

        @NotEmpty(message = "Details cannot be empty")
        String details
) {}
