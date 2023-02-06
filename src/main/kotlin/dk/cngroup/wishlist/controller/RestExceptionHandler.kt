package dk.cngroup.wishlist.controller

import mu.KotlinLogging
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(EmptyResultDataAccessException::class)
    @ResponseStatus(NOT_FOUND)
    fun handleNotFoundException(e: Exception): String? {
        return e.message
    }

    @ExceptionHandler(Throwable::class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    fun handleAnyException(t: Throwable): String? {
        logger.error("Internal server error", t)
        return t.message
    }
}

private val logger = KotlinLogging.logger {}