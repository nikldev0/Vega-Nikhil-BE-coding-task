package com.vega.be_coding_task_nikhil.model.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;

public record InstitutionalInvestorDetailsDTO(
        UUID investorId,

        @NotEmpty(message = "Company name is required")
        String companyName,

        @NotEmpty(message = "Country of incorporation is required")
        String countryOfIncorporation
) {}
