package com.vega.be_coding_task_nikhil.repository;

import java.util.Optional;
import java.util.UUID;

import com.vega.be_coding_task_nikhil.model.entity.Fund;
import com.vega.be_coding_task_nikhil.model.entity.OnboardingFlow;
import com.vega.be_coding_task_nikhil.model.enums.InvestorType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnboardingFlowRepository extends JpaRepository<OnboardingFlow, UUID> {

    Optional<OnboardingFlow> findByFundAndInvestorType(Fund fund, InvestorType investorType);

}
