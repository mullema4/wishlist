package dk.cngroup.wishlist.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(EmptyResultDataAccessException::class)
    @ResponseStatus(NOT_FOUND)
    fun handleNotFoundException(exception: Exception) = exception.typeAndMessage()

    @ExceptionHandler(Throwable::class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    fun handleAnyException(throwable: Throwable): String? {
        logger.error(throwable) { "Internal server error" }
        return throwable.typeAndMessage()
    }

    fun Throwable.typeAndMessage() = "${this::class.simpleName}: ${this.message}"
}

private val logger = KotlinLogging.logger {}