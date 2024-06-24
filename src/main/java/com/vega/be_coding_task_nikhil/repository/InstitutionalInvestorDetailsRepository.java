package com.vega.be_coding_task_nikhil.repository;

import java.util.UUID;

import com.vega.be_coding_task_nikhil.model.entity.InstitutionalInvestorDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionalInvestorDetailsRepository extends JpaRepository<InstitutionalInvestorDetails, UUID> {
}
