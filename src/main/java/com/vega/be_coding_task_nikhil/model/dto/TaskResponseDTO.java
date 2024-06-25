package com.vega.be_coding_task_nikhil.model.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.vega.be_coding_task_nikhil.model.enums.TaskResponseStatus;

public record TaskResponseDTO(UUID responseId, UUID subscriptionId, UUID taskId, TaskResponseStatus status, List<UUID> questionResponseIds, Instant completedAt) {}
