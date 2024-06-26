package com.vega.be_coding_task_nikhil.repository;

import java.util.UUID;

import com.vega.be_coding_task_nikhil.model.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, UUID> {
}
