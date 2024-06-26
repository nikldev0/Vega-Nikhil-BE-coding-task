package com.vega.be_coding_task_nikhil.service.onboardingFlow;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.vega.be_coding_task_nikhil.exception.ResourceNotFoundException;
import com.vega.be_coding_task_nikhil.model.dto.OnboardingFlowDTO;
import com.vega.be_coding_task_nikhil.model.dto.TaskDTO;
import com.vega.be_coding_task_nikhil.model.entity.Fund;
import com.vega.be_coding_task_nikhil.model.entity.OnboardingFlow;
import com.vega.be_coding_task_nikhil.model.entity.Question;
import com.vega.be_coding_task_nikhil.model.entity.Task;
import com.vega.be_coding_task_nikhil.model.enums.InvestorType;
import com.vega.be_coding_task_nikhil.repository.FundRepository;
import com.vega.be_coding_task_nikhil.repository.OnboardingFlowRepository;
import com.vega.be_coding_task_nikhil.repository.QuestionRepository;
import com.vega.be_coding_task_nikhil.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for handling onboarding flow logic.
 */
@Service
public class OnboardingFlowServiceImpl implements OnboardingFlowService {

    private final OnboardingFlowRepository onboardingFlowRepository;
    private final TaskRepository taskRepository;
    private final FundRepository fundRepository;
    private final QuestionRepository questionRepository;


    @Autowired
    public OnboardingFlowServiceImpl(OnboardingFlowRepository onboardingFlowRepository, TaskRepository taskRepository, FundRepository fundRepository, QuestionRepository questionRepository) {
        this.onboardingFlowRepository = onboardingFlowRepository;
        this.taskRepository = taskRepository;
        this.fundRepository = fundRepository;
        this.questionRepository = questionRepository;
    }


    /**
     * Creates a new onboarding flow using the provided DTO and persists it to the database.
     *
     * @param dto the data transfer object containing the necessary data
     * @return the created OnboardingFlowDTO with persisted data
     */
    @Transactional
    @Override
    public OnboardingFlowDTO createOnboardingFlow(OnboardingFlowDTO dto) {
        Fund fund = fundRepository.findById(dto.fundId())
                .orElseThrow(() -> new ResourceNotFoundException("Fund not found"));

        OnboardingFlow onboardingFlow = new OnboardingFlow();
        onboardingFlow.setFund(fund);
        onboardingFlow.setInvestorType(dto.investorType());
        onboardingFlow.setMinimumInvestment(dto.minimumInvestment());
        onboardingFlow.setTasks(convertTasksDtoToEntity(dto.taskIds()));

        try {
            OnboardingFlow savedFlow = onboardingFlowRepository.save(onboardingFlow);
            return convertToDTO(savedFlow);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Database error: " + e.getMessage());
        }
    }


    /**
     * Converts the OnboardingFlow entity to its corresponding DTO.
     *
     * @param onboardingFlow the entity to convert
     * @return the DTO representation of the onboarding flow
     */
    private OnboardingFlowDTO mapOnboardingFlowEntityToDTO(OnboardingFlow onboardingFlow) {
        return new OnboardingFlowDTO(
                onboardingFlow.getFlowId(),
                onboardingFlow.getFund().getFundId(),
                onboardingFlow.getInvestorType(),
                onboardingFlow.getTasks().stream().map(Task::getTaskId).collect(Collectors.toList()),
                onboardingFlow.getMinimumInvestment()
        );
    }

    /**
     * Helper method to convert task IDs in DTO to Task entities.
     *
     * @param taskIds List of task UUIDs from the DTO
     * @return List of Task entities
     */
    private List<Task> convertTasksDtoToEntity(List<UUID> taskIds) {
        // This should retrieve and collect existing tasks based on ids or create new tasks if necessary
        return taskIds.stream()
                .map(this::findTaskById)
                .collect(Collectors.toList());
    }


    /**
     * Updates the minimum investment amount for the specified onboarding flow.
     *
     * @param flowId The UUID of the onboarding flow to be updated.
     * @param newMinimumInvestment The new minimum investment amount.
     * @return The updated OnboardingFlowDTO.
     * @throws RuntimeException If the onboarding flow is not found.
     */
    @Transactional
    @Override
    public OnboardingFlowDTO updateMinimumInvestment(UUID flowId, BigDecimal newMinimumInvestment) {
        OnboardingFlow onboardingFlow = findOnboardingFlowById(flowId);
        onboardingFlow.setMinimumInvestment(newMinimumInvestment);
        return convertToDTO(onboardingFlowRepository.save(onboardingFlow));
    }

    /**
     * Updates the investor type for the specified onboarding flow.
     *
     * @param flowId The UUID of the onboarding flow to be updated.
     * @param newInvestorType The new investor type.
     * @return The updated OnboardingFlowDTO.
     * @throws RuntimeException If the onboarding flow is not found.
     */
    @Transactional
    @Override
    public OnboardingFlowDTO updateInvestorType(UUID flowId, InvestorType newInvestorType) {
        OnboardingFlow onboardingFlow = findOnboardingFlowById(flowId);
        onboardingFlow.setInvestorType(newInvestorType);
        return convertToDTO(onboardingFlowRepository.save(onboardingFlow));
    }

    /**
     * Adds a new task to the specified onboarding flow.
     *
     * @param flowId The UUID of the onboarding flow to be updated.
     * @param newTaskDTO The DTO containing details of the new task.
     * @return The updated OnboardingFlowDTO.
     * @throws RuntimeException If the onboarding flow is not found.
     */
    @Transactional
    @Override
    public OnboardingFlowDTO addTaskToFlow(UUID flowId, TaskDTO newTaskDTO) {
        OnboardingFlow onboardingFlow = findOnboardingFlowById(flowId);
        Task newTask = new Task();
        newTask.setOnboardingFlow(onboardingFlow);
        newTask.setDescription(newTaskDTO.description());
        newTask.setQuestions(convertQuestionIdsToEntities(newTaskDTO.questionIds(), newTask));

        onboardingFlow.getTasks().add(newTask);
        return convertToDTO(onboardingFlowRepository.save(onboardingFlow));
    }


    /**
     * Converts a list of question IDs to Question entities.
     *
     * @param questionIds The list of question IDs.
     * @param task The task to associate with the questions.
     * @return The list of Question entities.
     * @throws RuntimeException If any question is not found.
     */
    private List<Question> convertQuestionIdsToEntities(List<UUID> questionIds, Task task) {
        return questionIds.stream()
                .map(id -> questionRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Question not found for ID: " + id)))
                .peek(question -> question.setTask(task))
                .collect(Collectors.toList());
    }

    /**
     * Removes an existing task from the specified onboarding flow.
     *
     * @param flowId The UUID of the onboarding flow to be updated.
     * @param taskId The UUID of the task to be removed.
     * @return The updated OnboardingFlowDTO.
     * @throws RuntimeException If the onboarding flow or task is not found.
     */
    @Transactional
    @Override
    public OnboardingFlowDTO removeTaskFromFlow(UUID flowId, UUID taskId) {
        OnboardingFlow onboardingFlow = findOnboardingFlowById(flowId);
        Task task = findTaskById(taskId);

        onboardingFlow.getTasks().remove(task);
        taskRepository.delete(task);
        return convertToDTO(onboardingFlowRepository.save(onboardingFlow));
    }

    /**
     * Updates an existing task in the specified onboarding flow.
     *
     * @param flowId The UUID of the onboarding flow to be updated.
     * @param updatedTaskDTO The DTO containing the updated details of the task.
     * @return The updated OnboardingFlowDTO.
     * @throws RuntimeException If the onboarding flow or task is not found.
     */
    @Transactional
    @Override
    public OnboardingFlowDTO updateTaskInFlow(UUID flowId, TaskDTO updatedTaskDTO) {
        OnboardingFlow onboardingFlow = findOnboardingFlowById(flowId);
        Task task = findTaskById(updatedTaskDTO.taskId());

        task.setDescription(updatedTaskDTO.description());
        task.setQuestions(convertQuestionIdsToEntities(updatedTaskDTO.questionIds(), task));
        taskRepository.save(task);

        return convertToDTO(onboardingFlow);
    }

    private OnboardingFlow findOnboardingFlowById(UUID flowId) {
        return onboardingFlowRepository.findById(flowId)
                .orElseThrow(() -> new ResourceNotFoundException("Onboarding Flow not found"));
    }

    private Task findTaskById(UUID taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    private OnboardingFlowDTO convertToDTO(OnboardingFlow onboardingFlow) {
        return new OnboardingFlowDTO(
                onboardingFlow.getFlowId(),
                onboardingFlow.getFund().getFundId(),
                onboardingFlow.getInvestorType(),
                onboardingFlow.getTasks().stream().map(Task::getTaskId).collect(Collectors.toList()),
                onboardingFlow.getMinimumInvestment()
        );
    }

}

