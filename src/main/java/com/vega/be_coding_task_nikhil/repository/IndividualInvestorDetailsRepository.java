package com.vega.be_coding_task_nikhil.repository;

import java.util.UUID;

import com.vega.be_coding_task_nikhil.model.entity.IndividualInvestorDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndividualInvestorDetailsRepository extends JpaRepository<IndividualInvestorDetails, UUID> {
}
