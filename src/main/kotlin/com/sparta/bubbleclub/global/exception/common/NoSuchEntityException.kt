package com.sparta.bubbleclub.global.exception.common

import com.sparta.bubbleclub.global.exception.code.ErrorCode

class NoSuchEntityException(
    override val errorCode: ErrorCode = CommonErrorCode.ENTITY_NOT_FOUND_ERR,
    override val errorMessage: String
) : RestApiException(errorMessage) {
}
