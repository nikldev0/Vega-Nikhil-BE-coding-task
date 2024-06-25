package com.vega.be_coding_task_nikhil.controller;

import java.util.List;
import java.util.UUID;

import com.vega.be_coding_task_nikhil.model.dto.FundSubscriptionDTO;
import com.vega.be_coding_task_nikhil.model.dto.SubscriptionDTO;
import com.vega.be_coding_task_nikhil.model.dto.SubscriptionInvestmentDTO;
import com.vega.be_coding_task_nikhil.service.subscription.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subscriptions")
@Validated
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Operation(summary = "Subscribe an investor to a fund")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription created successfully"),
            @ApiResponse(responseCode = "404", description = "Investor or Fund not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<SubscriptionDTO> subscribeToFund(
            @RequestParam UUID investorId,
            @RequestParam UUID fundId) {
        SubscriptionDTO subscription = subscriptionService.subscribeToFund(investorId, fundId);
        return ResponseEntity.ok(subscription);
    }


    @Operation(summary = "Get state of active subscriptions with total intended investment volume per fund")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the state of active subscriptions"),
    })
    @GetMapping("/active/total-investment")
    public ResponseEntity<List<FundSubscriptionDTO>> getActiveSubscriptionsWithTotalInvestmentVolume() {
        List<FundSubscriptionDTO> subscriptions = subscriptionService.getActiveSubscriptionsWithTotalInvestmentVolume();
        return ResponseEntity.ok(subscriptions);
    }


    @Operation(summary = "Get state of active subscriptions with total intended investment volume per subscription")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the state of active subscriptions"),
    })
    @GetMapping("/active/investment-per-subscription")
    public ResponseEntity<List<SubscriptionInvestmentDTO>> getActiveSubscriptionsWithTotalInvestmentVolumePerSubscription() {
        List<SubscriptionInvestmentDTO> subscriptions = subscriptionService.getActiveSubscriptionsWithTotalInvestmentVolumePerSubscription();
        return ResponseEntity.ok(subscriptions);
    }


}
