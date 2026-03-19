package br.dev.as.task_service;

import br.dev.as.task_service.tasks.api.TasksApi;
import br.dev.as.task_service.tasks.dto.CreateTaskRequestDTO;
import br.dev.as.task_service.tasks.dto.PaginatedResultDTO;
import br.dev.as.task_service.tasks.dto.TaskDTO;
import br.dev.as.task_service.tasks.dto.UpdateTaskRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TaskController implements TasksApi {

    private final TaskService taskService;

    @Override
    public ResponseEntity<Void>cancelTask(final Long taskId) {
        taskService.cancelTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void>completeTask(final Long taskId) {
        taskService.completeTask(taskId);
        return ResponseEntity.noContent().build();
    }


    @Override
    public ResponseEntity<TaskDTO> createTask(@Valid final CreateTaskRequestDTO body) {
        return ResponseEntity.ok(taskService.createTask(body));
    }

    @Override
    public ResponseEntity<Void>executeTask(final Long taskId) {
        taskService.executeTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<PaginatedResultDTO>getAllTasks(@Valid final Integer page,
                                                         @Valid final Integer size, @Valid final String sort, @Valid final String status) {
        return ResponseEntity.ok(taskService.getAllTasks(page, size, sort, status));
    }

    @Override
    public ResponseEntity<TaskDTO>getTask(final Long taskId) {
        return ResponseEntity.ok(taskService.getTask(taskId));
    }

    @Override
    public ResponseEntity<TaskDTO>updateTask(final Long taskId,
                                             @Valid final UpdateTaskRequestDTO body) {
        return ResponseEntity.ok(taskService.updateTask(taskId, body));
    }
}