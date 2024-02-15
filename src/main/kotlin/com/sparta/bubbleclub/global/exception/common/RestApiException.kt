package com.sparta.bubbleclub.global.exception.common

import com.sparta.bubbleclub.global.exception.code.ErrorCode
import java.lang.RuntimeException

abstract class RestApiException : RuntimeException {

    abstract val errorCode: ErrorCode

    abstract val errorMessage: String

    constructor() : super()

    constructor(errorMessage: String) : super(errorMessage)
    
}