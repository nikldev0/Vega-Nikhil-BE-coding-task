package com.vega.be_coding_task_nikhil.repository;

import java.util.UUID;

import com.vega.be_coding_task_nikhil.model.entity.OnboardingFlow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnboardingFlowRepository extends JpaRepository<OnboardingFlow, UUID> {

}
