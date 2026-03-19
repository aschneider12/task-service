package br.dev.as.task_service.exception;

public class TaskAlreadyInProgressException extends RuntimeException {

    public TaskAlreadyInProgressException(final Long id) {
        super("Task with id " + id + " is in progress");
    }

}