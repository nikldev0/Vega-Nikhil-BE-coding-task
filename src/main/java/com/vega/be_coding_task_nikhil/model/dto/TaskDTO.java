package com.vega.be_coding_task_nikhil.model.dto;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record TaskDTO(
        UUID taskId,

        @NotNull(message = "Onboarding flow ID is required")
        UUID onboardingFlowId,

        @NotEmpty(message = "Task description is required")
        String description,

        @NotNull(message = "Question IDs list cannot be null")
        @NotEmpty(message = "At least one question ID is required")
        List<UUID> questionIds
) {}