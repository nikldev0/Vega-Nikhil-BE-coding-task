package com.vega.be_coding_task_nikhil.controller;


import java.math.BigDecimal;
import java.util.UUID;

import com.vega.be_coding_task_nikhil.model.dto.OnboardingFlowDTO;
import com.vega.be_coding_task_nikhil.model.dto.TaskDTO;
import com.vega.be_coding_task_nikhil.model.enums.InvestorType;
import com.vega.be_coding_task_nikhil.service.OnboardingFlowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing onboarding flows associated with various funds.
 */
@RestController
@RequestMapping("/api/onboarding-flows")
@Tag(name = "OnboardingFlowController", description = "The Onboarding Flow API")
public class OnboardingFlowController {

    private final OnboardingFlowService onboardingFlowService;

    @Autowired
    public OnboardingFlowController(OnboardingFlowService onboardingFlowService) {
        this.onboardingFlowService = onboardingFlowService;
    }

    /**
     * Creates a new onboarding flow for a specific fund.
     *
     * @param onboardingFlowDTO the onboarding flow data transfer object containing details needed to create the flow
     * @return ResponseEntity with status 201 (Created) if the onboarding flow is successfully created, including the created onboardingFlowDTO
     */
    @PostMapping
    @Operation(summary = "Create a new onboarding flow", description = "Creates a new onboarding flow for a specific fund",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Onboarding flow created successfully",
                            content = @Content(schema = @Schema(implementation = OnboardingFlowDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            })
    public ResponseEntity<OnboardingFlowDTO> createOnboardingFlow(@Valid @RequestBody OnboardingFlowDTO onboardingFlowDTO) {
        OnboardingFlowDTO createdOnboardingFlow = onboardingFlowService.createOnboardingFlow(onboardingFlowDTO);
        return new ResponseEntity<>(createdOnboardingFlow, HttpStatus.CREATED);
    }

    @Operation(summary = "Update minimum investment amount for an onboarding flow")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Minimum investment updated successfully"),
            @ApiResponse(responseCode = "404", description = "Onboarding flow not found")
    })
    @PatchMapping("/{flowId}/minimum-investment")
    public ResponseEntity<OnboardingFlowDTO> updateMinimumInvestment(
            @PathVariable UUID flowId,
            @RequestParam BigDecimal newMinimumInvestment) {
        OnboardingFlowDTO updatedFlow = onboardingFlowService.updateMinimumInvestment(flowId, newMinimumInvestment);
        return ResponseEntity.ok(updatedFlow);
    }

    @Operation(summary = "Update investor type for an onboarding flow")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Investor type updated successfully"),
            @ApiResponse(responseCode = "404", description = "Onboarding flow not found")
    })
    @PatchMapping("/{flowId}/investor-type")
    public ResponseEntity<OnboardingFlowDTO> updateInvestorType(
            @PathVariable UUID flowId,
            @RequestParam InvestorType newInvestorType) {
        OnboardingFlowDTO updatedFlow = onboardingFlowService.updateInvestorType(flowId, newInvestorType);
        return ResponseEntity.ok(updatedFlow);
    }

    @Operation(summary = "Add a new task to an onboarding flow")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task added successfully"),
            @ApiResponse(responseCode = "404", description = "Onboarding flow not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/{flowId}/tasks/add")
    public ResponseEntity<OnboardingFlowDTO> addTaskToFlow(
            @PathVariable UUID flowId,
            @Valid @RequestBody TaskDTO newTask) {
        OnboardingFlowDTO updatedFlow = onboardingFlowService.addTaskToFlow(flowId, newTask);
        return ResponseEntity.ok(updatedFlow);
    }

    @Operation(summary = "Remove a task from an onboarding flow")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task removed successfully"),
            @ApiResponse(responseCode = "404", description = "Onboarding flow or task not found")
    })
    @DeleteMapping("/{flowId}/tasks/remove/{taskId}")
    public ResponseEntity<OnboardingFlowDTO> removeTaskFromFlow(
            @PathVariable UUID flowId,
            @PathVariable UUID taskId) {
        OnboardingFlowDTO updatedFlow = onboardingFlowService.removeTaskFromFlow(flowId, taskId);
        return ResponseEntity.ok(updatedFlow);
    }

    @Operation(summary = "Update an existing task in an onboarding flow")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated successfully"),
            @ApiResponse(responseCode = "404", description = "Onboarding flow or task not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{flowId}/tasks/update")
    public ResponseEntity<OnboardingFlowDTO> updateTaskInFlow(
            @PathVariable UUID flowId,
            @Valid @RequestBody TaskDTO updatedTask) {
        OnboardingFlowDTO updatedFlow = onboardingFlowService.updateTaskInFlow(flowId, updatedTask);
        return ResponseEntity.ok(updatedFlow);
    }
}
