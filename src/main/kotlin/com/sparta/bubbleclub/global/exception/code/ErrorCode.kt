package com.sparta.bubbleclub.global.exception.code

import org.springframework.http.HttpStatus

interface ErrorCode {
    fun errorName(): String
    fun status(): HttpStatus
    fun bodyMessage(): String
}
