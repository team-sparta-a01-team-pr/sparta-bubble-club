package com.sparta.bubbleclub.global.exception.common

import com.sparta.bubbleclub.global.exception.code.ErrorCode

class CustomIllegalArgumentException(
    override val errorCode: ErrorCode = CommonErrorCode.INVALID_ARGS_ERR,
    override val errorMessage: String = errorCode.bodyMessage
) : RestApiException(errorMessage) {
}