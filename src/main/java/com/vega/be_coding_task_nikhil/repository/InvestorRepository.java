package com.vega.be_coding_task_nikhil.repository;

import java.util.UUID;

import com.vega.be_coding_task_nikhil.model.entity.Investor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestorRepository extends JpaRepository<Investor, UUID> {
}
