package br.dev.as.task_service.exception;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(final Long id) {
        super("Task with id " + id + " not found");
    }

}