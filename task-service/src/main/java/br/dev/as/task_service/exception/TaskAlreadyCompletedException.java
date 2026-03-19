package br.dev.as.task_service.exception;

public class TaskAlreadyCompletedException extends RuntimeException {

    public TaskAlreadyCompletedException(final Long id) {
        super("Task with id " + id + " is already completed");
    }

}