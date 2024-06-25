package com.vega.be_coding_task_nikhil.service.subscription;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.vega.be_coding_task_nikhil.model.dto.FundSubscriptionDTO;
import com.vega.be_coding_task_nikhil.model.dto.SubscriptionDTO;
import com.vega.be_coding_task_nikhil.model.dto.SubscriptionInvestmentDTO;
import com.vega.be_coding_task_nikhil.model.entity.Fund;
import com.vega.be_coding_task_nikhil.model.entity.Investor;
import com.vega.be_coding_task_nikhil.model.entity.OnboardingFlow;
import com.vega.be_coding_task_nikhil.model.entity.QuestionResponse;
import com.vega.be_coding_task_nikhil.model.entity.Subscription;
import com.vega.be_coding_task_nikhil.model.entity.TaskResponse;
import com.vega.be_coding_task_nikhil.model.enums.SubscriptionStatus;
import com.vega.be_coding_task_nikhil.model.enums.TaskResponseStatus;
import com.vega.be_coding_task_nikhil.repository.FundRepository;
import com.vega.be_coding_task_nikhil.repository.InvestorRepository;
import com.vega.be_coding_task_nikhil.repository.OnboardingFlowRepository;
import com.vega.be_coding_task_nikhil.repository.QuestionRepository;
import com.vega.be_coding_task_nikhil.repository.QuestionResponseRepository;
import com.vega.be_coding_task_nikhil.repository.SubscriptionRepository;
import com.vega.be_coding_task_nikhil.repository.TaskRepository;
import com.vega.be_coding_task_nikhil.repository.TaskResponseRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final InvestorRepository investorRepository;
    private final FundRepository fundRepository;
    private final OnboardingFlowRepository onboardingFlowRepository;
    private final TaskRepository taskRepository;
    private final TaskResponseRepository taskResponseRepository;
    private final QuestionRepository questionRepository;
    private final QuestionResponseRepository questionResponseRepository;


    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository,
                                   InvestorRepository investorRepository,
                                   FundRepository fundRepository,
                                   OnboardingFlowRepository onboardingFlowRepository,
                                   TaskRepository taskRepository,
                                   TaskResponseRepository taskResponseRepository, QuestionRepository questionRepository, QuestionResponseRepository questionResponseRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.investorRepository = investorRepository;
        this.fundRepository = fundRepository;
        this.onboardingFlowRepository = onboardingFlowRepository;
        this.taskRepository = taskRepository;
        this.taskResponseRepository = taskResponseRepository;
        this.questionRepository = questionRepository;
        this.questionResponseRepository = questionResponseRepository;
    }

    @Transactional
    @Override
    public SubscriptionDTO subscribeToFund(UUID investorId, UUID fundId) {
        Investor investor = investorRepository.findById(investorId)
                .orElseThrow(() -> new RuntimeException("Investor not found"));

        Fund fund = fundRepository.findById(fundId)
                .orElseThrow(() -> new RuntimeException("Fund not found"));

        // Check if the investor already has a subscription to this fund
        boolean alreadySubscribed = subscriptionRepository.existsByInvestorAndFund(investor, fund);
        if (alreadySubscribed) {
            throw new RuntimeException("Investor is already subscribed to this fund");
        }

        // Create a new subscription
        Subscription subscription = new Subscription();
        subscription.setInvestor(investor);
        subscription.setFund(fund);
        subscription.setStatus(SubscriptionStatus.ACTIVE);
        subscription.setCreatedAt(Instant.now());
        subscription.setLastUpdated(Instant.now());

        Subscription savedSubscription = subscriptionRepository.save(subscription);

        // Create TaskResponses for each task in the fund's onboarding flow
        OnboardingFlow onboardingFlow = onboardingFlowRepository.findByFundAndInvestorType(fund, investor.getType())
                .orElseThrow(() -> new RuntimeException("Onboarding flow not found"));

        onboardingFlow.getTasks().forEach(task -> {
            TaskResponse taskResponse = new TaskResponse();
            taskResponse.setSubscription(savedSubscription);
            taskResponse.setTask(task);
            taskResponse.setStatus(TaskResponseStatus.PENDING);
            taskResponseRepository.save(taskResponse);

            // Create empty QuestionResponses for each question in the task
            task.getQuestions().forEach(question -> {
                QuestionResponse questionResponse = new QuestionResponse();
                questionResponse.setTaskResponse(taskResponse);
                questionResponse.setQuestion(question);
                questionResponse.setAnswer("");
                questionResponse.setCompleted(false);
                questionResponseRepository.save(questionResponse);
            });
        });

        return mapSubscriptionToDTO(savedSubscription);
    }

    // method should be executed within a transactional context with a read-only status.
    @Transactional(readOnly = true)
    @Override
    public List<FundSubscriptionDTO> getActiveSubscriptionsWithTotalInvestmentVolume() {
        return subscriptionRepository.findActiveSubscriptionsWithTotalInvestmentVolume();
    }

    // method should be executed within a transactional context with a read-only status.
    @Transactional(readOnly = true)
    @Override
    public List<SubscriptionInvestmentDTO> getActiveSubscriptionsWithTotalInvestmentVolumePerSubscription() {
        return subscriptionRepository.findActiveSubscriptionsWithTotalInvestmentVolumePerSubscription();
    }

    private SubscriptionDTO mapSubscriptionToDTO(Subscription subscription) {
        return new SubscriptionDTO(
                subscription.getSubscriptionId(),
                subscription.getInvestor().getInvestorId(),
                subscription.getFund().getFundId(),
                subscription.getStatus(),
                subscription.getCreatedAt(),
                subscription.getLastUpdated()
        );
    }
}
