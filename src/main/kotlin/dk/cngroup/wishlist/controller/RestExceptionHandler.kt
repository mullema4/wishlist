package dk.cngroup.wishlist.controller

import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus.*
import org.springframework.http.HttpStatusCode
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import tools.jackson.databind.DatabindException

@RestControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(
        exception: MethodArgumentNotValidException,
        headers: HttpHeaders,
        statusCode: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<in Any>? {
        val problemDetail = exception.updateAndGetBody(messageSource, LocaleContextHolder.getLocale())
        problemDetail.detail = "One or more fields are invalid"
        problemDetail.setProperty("errors", exception.getInvalidFieldsAndMessages())
        return createResponseEntity(problemDetail, headers, statusCode, request)
    }

    override fun handleHttpMessageNotReadable(
        exception: HttpMessageNotReadableException,
        headers: HttpHeaders,
        statusCode: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<in Any>? {
        val problemDetail = createProblemDetail(exception, statusCode, "Invalid request payload", null, null, request)

        when (val cause = exception.cause) {
            is DatabindException -> {
                problemDetail.detail = "One or more fields are missing or invalid"
                problemDetail.setProperty("errors", cause.getInvalidFieldsAndMessages())
            }
        }

        return createResponseEntity(problemDetail, headers, statusCode, request)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleClientSideException(exception: Exception): ProblemDetail =
        ProblemDetail.forStatusAndDetail(BAD_REQUEST, exception.typeAndMessage())

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityException(exception: DataIntegrityViolationException): ProblemDetail =
        ProblemDetail.forStatusAndDetail(BAD_REQUEST, exception.rootCause!!.typeAndMessage())

    @ExceptionHandler(EmptyResultDataAccessException::class)
    fun handleNotFoundException(exception: Exception): ProblemDetail =
        ProblemDetail.forStatusAndDetail(NOT_FOUND, exception.typeAndMessage())

    @ExceptionHandler(Throwable::class)
    fun handleAnyException(throwable: Throwable): ProblemDetail {
        logger.error("Internal server error", throwable)
        return ProblemDetail.forStatusAndDetail(INTERNAL_SERVER_ERROR, throwable.typeAndMessage())
    }

    fun Throwable.typeAndMessage() = "${this::class.simpleName}: ${this.message}"

    private fun BindException.getInvalidFieldsAndMessages(): List<Map<String, String>> =
        bindingResult.fieldErrors.map { error ->
            mapOf("field" to error.field, "message" to (error.defaultMessage ?: "Invalid value"))
        }

    private fun DatabindException.getInvalidFieldsAndMessages(): List<Map<String, String>> =
        mapOf(
            "field" to path.joinToString(".") { ref ->
                ref.propertyName ?: if (ref.index >= 0) "[${ref.index}]" else "?"
            },
            "message" to "Missing or invalid value",
        ).let { map -> listOf(map) }
}