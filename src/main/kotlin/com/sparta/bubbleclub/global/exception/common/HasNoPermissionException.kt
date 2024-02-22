package com.sparta.bubbleclub.global.exception.common

import com.sparta.bubbleclub.global.exception.code.ErrorCode

class HasNoPermissionException(
    override val errorCode: ErrorCode = CommonErrorCode.HAS_NO_PERMISSION,
    override val errorMessage: String = errorCode.bodyMessage
) : RestApiException() {
}
