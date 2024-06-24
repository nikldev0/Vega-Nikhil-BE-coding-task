package com.vega.be_coding_task_nikhil.model.dto;

import java.math.BigDecimal;
import java.util.UUID;
public record FundDTO(UUID fundId, String name, BigDecimal minimumInvestmentAmount) {}

