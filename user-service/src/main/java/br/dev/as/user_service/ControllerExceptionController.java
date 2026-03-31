package br.dev.as.user_service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionController {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ProblemDetail handleUserAlreadyExistsException(final UserAlreadyExistsException e) {
        final var problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("User already exists");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("email", e.getMessage().substring(e.getMessage().indexOf(":") + 2));
        return problemDetail;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleUserNotFoundException(final UserNotFoundException e) {
        final var problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("User not found");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setProperty("id", e.getMessage().substring(e.getMessage().indexOf(":") + 2));
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        final var invalidFields = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(field -> new InvalidField(field.getField(), field.getDefaultMessage()))
                .toList();
        final var problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Bad Request");
        problem.setDetail("Invalid fields");
        problem.setProperty ("invalidFields", invalidFields);
        return problem;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleRuntimeException(final Exception e) {
        final var problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problem.setTitle("Internal Server Error");
        problem.setDetail(e.getMessage());
        return problem;
    }

    private record InvalidField(String field, String message) {}
}