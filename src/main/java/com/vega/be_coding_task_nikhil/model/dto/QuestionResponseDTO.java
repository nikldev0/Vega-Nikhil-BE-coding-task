package com.vega.be_coding_task_nikhil.model.dto;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record QuestionResponseDTO(
        UUID responseId,

        @NotNull(message = "Task response ID is required")
        UUID taskResponseId,

        @NotNull(message = "Question ID is required")
        UUID questionId,

        @NotEmpty(message = "Answer cannot be empty")
        String answer,

        @NotNull(message = "Completed field is required")
        Boolean completed,

        Instant answeredAt
) {}
