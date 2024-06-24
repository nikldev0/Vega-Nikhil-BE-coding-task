package com.vega.be_coding_task_nikhil.model.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "individual_investor_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndividualInvestorDetails {

    @Id
    @Column(name = "investor_id")
    private UUID investorId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String countryOfResidence;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "investor_id")
    private Investor investor;

}

