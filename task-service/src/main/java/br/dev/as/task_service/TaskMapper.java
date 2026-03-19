package br.dev.as.task_service;

import br.dev.as.task_service.tasks.dto.CreateTaskRequestDTO;
import br.dev.as.task_service.tasks.dto.TaskDTO;

public class TaskMapper {

    private TaskMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static Task toEntity(final CreateTaskRequestDTO createTaskRequestDTO) {
        return Task.builder()
                .title(createTaskRequestDTO.getTitle())
                .description(createTaskRequestDTO.getDescription())
                .build();
    }

    public static TaskDTO toDTO(final Task task) {
        return new TaskDTO()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(TaskDTO.StatusEnum.fromValue(task.getStatus().name()));
    }
}