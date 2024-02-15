package com.sparta.bubbleclub.global.exception.common

import com.sparta.bubbleclub.global.exception.code.ErrorCode

class NoSuchEntityException(
    val errorCode: ErrorCode,
) : RuntimeException() {
}