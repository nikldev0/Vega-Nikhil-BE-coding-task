package com.vega.be_coding_task_nikhil.model.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record QuestionDTO(
        UUID questionId,

        @NotNull(message = "Task ID is required")
        UUID taskId,

        @NotEmpty(message = "Question text is required")
        String text,

        @NotNull(message = "Mandatory field is required")
        Boolean mandatory
) {}