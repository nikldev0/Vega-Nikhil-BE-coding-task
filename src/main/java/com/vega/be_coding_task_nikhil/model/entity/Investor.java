package com.vega.be_coding_task_nikhil.model.entity;

import java.util.UUID;

import com.vega.be_coding_task_nikhil.model.enums.InvestorType;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "investor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Investor {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID investorId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvestorType type;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private String details;
}
