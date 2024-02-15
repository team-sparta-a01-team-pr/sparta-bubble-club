package com.sparta.bubbleclub.global.exception.handler

import com.sparta.bubbleclub.global.exception.code.CommonErrorCode
import com.sparta.bubbleclub.global.exception.common.NoSuchEntityException
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
        val errorCode = CommonErrorCode.INTERNAL_SERVER_ERR
        val response = ErrorResponse.of(errorCode)

        logger.warn(errorCode.errorName, exception)

        return ResponseEntity.status(errorCode.status).body(response)
    }

    @ExceptionHandler
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errorCode = CommonErrorCode.REQUEST_INVALID_ERR
        val response = ErrorResponse.of(errorCode, exception.bindingResult)

        logger.warn(errorCode.errorName, exception)

        return ResponseEntity.status(errorCode.status).body(response)
    }

    @ExceptionHandler
    fun handleNoSuchEntityException(exception: NoSuchEntityException): ResponseEntity<ErrorResponse> {
        val errorCode = exception.errorCode
        val response = ErrorResponse.of(errorCode)

        logger.warn(errorCode.errorName, exception)

        return ResponseEntity.status(errorCode.status).body(response)
    }

}