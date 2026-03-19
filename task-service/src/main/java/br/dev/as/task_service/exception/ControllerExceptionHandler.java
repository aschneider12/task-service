package br.dev.as.task_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ProblemDetail handleTaskNotFoundException(final TaskNotFoundException e) {
        final var problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND.value());
        problemDetail.setTitle("Task not found");
        problemDetail.setDetail(e.getMessage());
        return problemDetail;
    }

    @ExceptionHandler({ TaskAlreadyCanceledException.class,
            TaskAlreadyInProgressException.class,
            TaskAlreadyCompletedException.class})
    public ProblemDetail handleTaskAlreadyCompletedException(final RuntimeException e) {
        final var problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST.value());
        problemDetail.setTitle("Invalid operation");
        problemDetail.setDetail(e.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        final var invalidFields = e.getFieldErrors()
                .stream()
                .map(field -> new InvalidField(field.getField(), field.getDefaultMessage()))
                .toList();

        final var problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Bad Request");
        problemDetail.setDetail("The request contains invalid fields");
        problemDetail.setProperty("invalid-fields", invalidFields);

        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(final Exception e) {
        final var problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        problemDetail.setTitle("Internal server error");
        problemDetail.setDetail(e.getMessage());
        return problemDetail;
    }

    private record InvalidField(String name, String message) {
    }
}