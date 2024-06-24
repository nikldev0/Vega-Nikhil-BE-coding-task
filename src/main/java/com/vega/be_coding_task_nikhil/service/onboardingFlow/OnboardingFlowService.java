package com.vega.be_coding_task_nikhil.service.onboardingFlow;

import java.math.BigDecimal;
import java.util.UUID;

import com.vega.be_coding_task_nikhil.model.dto.OnboardingFlowDTO;
import com.vega.be_coding_task_nikhil.model.dto.TaskDTO;
import com.vega.be_coding_task_nikhil.model.enums.InvestorType;

public interface OnboardingFlowService {
    OnboardingFlowDTO createOnboardingFlow(OnboardingFlowDTO onboardingFlowDTO);

    OnboardingFlowDTO updateMinimumInvestment(UUID flowId, BigDecimal newMinimumInvestment);

    OnboardingFlowDTO updateInvestorType(UUID flowId, InvestorType newInvestorType);

    OnboardingFlowDTO addTaskToFlow(UUID flowId, TaskDTO newTaskDTO);

    OnboardingFlowDTO removeTaskFromFlow(UUID flowId, UUID taskId);

    OnboardingFlowDTO updateTaskInFlow(UUID flowId, TaskDTO updatedTaskDTO);
}

