package com.vega.be_coding_task_nikhil.controller;

import com.vega.be_coding_task_nikhil.model.dto.IndividualInvestorDetailsDTO;
import com.vega.be_coding_task_nikhil.model.dto.InstitutionalInvestorDetailsDTO;
import com.vega.be_coding_task_nikhil.model.dto.InvestorDTO;
import com.vega.be_coding_task_nikhil.service.investor.InvestorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class InvestorController {

    private final InvestorService investorService;

    public InvestorController(InvestorService investorService) {
        this.investorService = investorService;
    }

    @Operation(summary = "Create a new individual investor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Individual investor created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/individual")
    public ResponseEntity<InvestorDTO> createIndividualInvestor(
            @Valid @RequestBody IndividualInvestorDetailsDTO individualInvestorDetailsDTO) {
        InvestorDTO createdInvestor = investorService.createIndividualInvestor(individualInvestorDetailsDTO);
        return ResponseEntity.ok(createdInvestor);
    }

    @Operation(summary = "Create a new institutional investor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Institutional investor created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/institutional")
    public ResponseEntity<InvestorDTO> createInstitutionalInvestor(
            @Valid @RequestBody InstitutionalInvestorDetailsDTO institutionalInvestorDetailsDTO) {
        InvestorDTO createdInvestor = investorService.createInstitutionalInvestor(institutionalInvestorDetailsDTO);
        return ResponseEntity.ok(createdInvestor);
    }



}
