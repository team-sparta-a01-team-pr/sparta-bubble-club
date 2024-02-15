package com.sparta.bubbleclub.global.exception.member

import com.sparta.bubbleclub.global.exception.code.ErrorCode
import com.sparta.bubbleclub.global.exception.common.RestApiException

class DuplicateArgumentException(
    override val errorCode: ErrorCode = MemberErrorCode.DUPLICATE_ARGS_ERR,
    override val errorMessage: String = errorCode.bodyMessage
) : RestApiException(errorMessage) {
}