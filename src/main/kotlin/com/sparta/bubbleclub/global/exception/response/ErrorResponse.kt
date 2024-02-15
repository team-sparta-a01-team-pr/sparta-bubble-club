package com.sparta.bubbleclub.global.exception.response

import com.sparta.bubbleclub.global.exception.code.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult

class ErrorResponse(
    val code: HttpStatus,
    val message: String,
    val errors: List<FieldError>?
) {

    data class FieldError(
        val field: String, // 필드명
        val value: String, // 실제 사용자가 넘긴 값
        val message: String // @NotNull 등 어노테이션 안의 message 필드
    ) {
        companion object {
            fun of(bindingResult: BindingResult): List<FieldError> {
                val errors = bindingResult.fieldErrors

                return errors.map {
                    FieldError(
                        it.field,
                        it.rejectedValue.toString(),
                        it.defaultMessage.toString()
                    )
                }
            }
        }
    }


    companion object {
        fun of(errorCode: ErrorCode) = ErrorResponse(
            code = errorCode.status,
            message = errorCode.bodyMessage,
            errors = null
        )

        fun of(errorCode: ErrorCode, errorMessage: String) = ErrorResponse(
            code = errorCode.status,
            message = errorMessage,
            errors = null
        )

        fun of(errorCode: ErrorCode, bindingResult: BindingResult) = ErrorResponse(
            code = errorCode.status,
            message = errorCode.bodyMessage,
            errors = FieldError.of(bindingResult)
        )
    }
}
