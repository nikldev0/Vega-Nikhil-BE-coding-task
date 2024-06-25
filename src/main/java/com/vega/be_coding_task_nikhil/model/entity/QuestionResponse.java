package com.vega.be_coding_task_nikhil.model.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "question_response")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class QuestionResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID responseId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "task_response_id", nullable = false)
    private TaskResponse taskResponse;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(nullable = false)
    private String answer;

    @Column(nullable = false)
    private Boolean completed;

    @Column(nullable = true, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant answeredAt;

    @PreUpdate
    @PostPersist
    private void updateTaskResponseLastUpdated() {
        taskResponse.setCompletedAt(Instant.now());
        taskResponse.getSubscription().setLastUpdated(Instant.now());
    }

}
