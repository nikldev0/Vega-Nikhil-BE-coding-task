package com.vega.be_coding_task_nikhil.model.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fund")
@Data
@NoArgsConstructor
public class Fund {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID fundId;

    @Column(nullable = false)
    private String name;

    @Column(name = "minimum_investment_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal minimumInvestmentAmount;

}
