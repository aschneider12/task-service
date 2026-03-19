package br.dev.as.task_service;

import br.dev.as.task_service.exception.TaskNotFoundException;
import br.dev.as.task_service.tasks.dto.CreateTaskRequestDTO;
import br.dev.as.task_service.tasks.dto.PaginatedResultDTO;
import br.dev.as.task_service.tasks.dto.TaskDTO;
import br.dev.as.task_service.tasks.dto.UpdateTaskRequestDTO;
import ch.qos.logback.core.joran.util.beans.BeanDescriptionFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class TaskService {

    @NonNull
    private final TaskRepository taskRepository;

    public TaskDTO createTask(final CreateTaskRequestDTO taskDTO) {
        final var toCreate = Task.newTask(
                taskDTO.getTitle(),
                taskDTO.getDescription());
        final var created = taskRepository.save(toCreate);
        return TaskMapper.toDTO(created);
    }

    public TaskDTO updateTask(final Long taskId, final UpdateTaskRequestDTO taskDTO) {
        final var toUpdate = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));

        toUpdate.setTitle(taskDTO.getTitle());
        toUpdate.setDescription(taskDTO.getDescription());
        final var updated = taskRepository.save(toUpdate);
        return TaskMapper.toDTO(updated);
    }

    public void cancelTask(final Long taskId) {
        final var toCancel = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        toCancel.cancel();
        taskRepository.save(toCancel);
    }

    @Transactional(readOnly = true)
    public PaginatedResultDTO getAllTasks(final Integer page,
                                         final Integer size,
                                         final String sort,
                                         final String status) {
        final var sortParams = sort.split(",");
        final var sortField = sortParams[0];
        final var sortDirection = sortParams[1];
        final var pageable = PageRequest.of(page,
                size,
                Sort.by(Sort.Direction.fromString(sortDirection), sortField));

        final var specification = TaskSpecification.create(status);
        final var tasks = taskRepository.findAll(specification, pageable);
        final var totalElements = tasks.getTotalElements();
        final var totalPages = tasks.getTotalPages();
        final var taskDTOs = tasks.getContent().stream()
                .map(TaskMapper::toDTO)
                .toList();
        return new PaginatedResultDTO()
                .page(page)
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .content(taskDTOs);
    }

    @Transactional(readOnly = true)
    public TaskDTO getTask(final Long taskId) {
        return taskRepository.findById(taskId)
                .map(TaskMapper::toDTO)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
    }

    public void executeTask(final Long taskId) {
        final var toExecute = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        toExecute.execute();
        taskRepository.save(toExecute);
    }

    public void completeTask(final Long taskId) {
        final var toComplete = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        toComplete.complete();
        taskRepository.save(toComplete);
    }

}