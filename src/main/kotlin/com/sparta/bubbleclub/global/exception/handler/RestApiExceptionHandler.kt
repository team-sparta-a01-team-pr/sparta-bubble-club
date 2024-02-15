package com.sparta.bubbleclub.global.exception.handler

import com.sparta.bubbleclub.global.exception.common.CommonErrorCode
import com.sparta.bubbleclub.global.exception.common.RestApiException
import com.sparta.bubbleclub.global.exception.response.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RestApiExceptionHandler {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler
    fun handleInternalServerException(exception: RuntimeException): ResponseEntity<ErrorResponse> {
        logger.warn(exception::class.simpleName, exception)
        return makeResponse(ErrorResponse.of(CommonErrorCode.INTERNAL_SERVER_ERR))
    }

    @ExceptionHandler
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        logger.warn(exception::class.simpleName, exception)
        return makeResponse(ErrorResponse.of(CommonErrorCode.INVALID_ARGS_ERR, exception.bindingResult))
    }

    @ExceptionHandler
    fun handleRestApiException(exception: RestApiException): ResponseEntity<ErrorResponse> {
        logger.warn(exception::class.simpleName, exception) // 추후 AOP 적용 예상
        return makeResponse(ErrorResponse.of(exception.errorCode, exception.errorMessage))
    }

    private fun makeResponse(response: ErrorResponse): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(response.code).body(response)
    }
}