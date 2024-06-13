package dk.cngroup.wishlist.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(NOT_FOUND)
    public String handleNotFoundException(Exception exception) {
        return getExceptionTypeAndMessage(exception);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public String handleAnyException(Throwable throwable) {
        log.error("Internal server error", throwable);
        return getExceptionTypeAndMessage(throwable);
    }

    private String getExceptionTypeAndMessage(Throwable throwable) {
        return throwable.getClass().getSimpleName() + ": " + throwable.getMessage();
    }
}