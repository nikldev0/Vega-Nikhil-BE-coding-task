package com.vega.be_coding_task_nikhil.model.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;

public record IndividualInvestorDetailsDTO(
        UUID investorId,

        @NotEmpty(message = "First name is required")
        String firstName,

        @NotEmpty(message = "Last name is required")
        String lastName,

        @NotEmpty(message = "Country of residence is required")
        String countryOfResidence
) {}