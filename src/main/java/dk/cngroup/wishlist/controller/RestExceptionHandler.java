package dk.cngroup.wishlist.controller;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tools.jackson.databind.DatabindException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request) {
        ProblemDetail problemDetail = exception.updateAndGetBody(getMessageSource(), LocaleContextHolder.getLocale());
        problemDetail.setDetail("One or more fields are invalid");
        problemDetail.setProperty("errors", getInvalidFieldsAndMessages(exception));
        return createResponseEntity(problemDetail, headers, statusCode, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException exception,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request) {
        ProblemDetail problemDetail = createProblemDetail(exception, statusCode, "Invalid request payload", null, null, request);

        if (exception.getCause() instanceof DatabindException cause) {
            problemDetail.setDetail("One or more fields are missing or invalid");
            problemDetail.setProperty("errors", getInvalidFieldsAndMessages(cause));
        }

        return createResponseEntity(problemDetail, headers, statusCode, request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleClientSideException(Exception exception) {
        return ProblemDetail.forStatusAndDetail(BAD_REQUEST, getExceptionTypeAndMessage(exception));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityException(DataIntegrityViolationException exception) {
        Throwable cause = exception.getRootCause() == null ? exception : exception.getRootCause();
        return ProblemDetail.forStatusAndDetail(BAD_REQUEST, getExceptionTypeAndMessage(cause));
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ProblemDetail handleNotFoundException(Exception exception) {
        return ProblemDetail.forStatusAndDetail(NOT_FOUND, getExceptionTypeAndMessage(exception));
    }

    @ExceptionHandler(Throwable.class)
    public ProblemDetail handleAnyException(Throwable throwable) {
        logger.error("Internal server error", throwable);
        return ProblemDetail.forStatusAndDetail(INTERNAL_SERVER_ERROR, getExceptionTypeAndMessage(throwable));
    }

    private String getExceptionTypeAndMessage(Throwable throwable) {
        return throwable.getClass().getSimpleName() + ": " + throwable.getMessage();
    }

    private List<Map<String, String>> getInvalidFieldsAndMessages(BindException exception) {
        return exception.getBindingResult().getFieldErrors().stream()
                .map(error -> Map.of(
                        "field", error.getField(),
                        "message", error.getDefaultMessage() == null ? "Invalid value" : error.getDefaultMessage()
                ))
                .toList();
    }

    private List<Map<String, String>> getInvalidFieldsAndMessages(DatabindException exception) {
        String field = exception.getPath().stream()
                .map(reference -> reference.getPropertyName() != null
                        ? reference.getPropertyName()
                        : reference.getIndex() >= 0 ? "[" + reference.getIndex() + "]" : "?")
                .collect(Collectors.joining("."));
        return List.of(Map.of(
                "field", field,
                "message", "Missing or invalid value"
        ));
    }
}
