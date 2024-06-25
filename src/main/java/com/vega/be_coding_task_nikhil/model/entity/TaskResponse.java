package com.vega.be_coding_task_nikhil.model.entity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.vega.be_coding_task_nikhil.model.enums.TaskResponseStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "task_response")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID responseId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subscription_id", nullable = false)
    private Subscription subscription;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskResponseStatus status;

    @OneToMany(mappedBy = "taskResponse", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuestionResponse> questionResponses;

    @Column(nullable = true, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant completedAt;

    @PreUpdate
    @PostPersist
    private void updateSubscriptionLastUpdated() {
        subscription.setLastUpdated(Instant.now());
    }

}
