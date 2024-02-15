package com.sparta.bubbleclub.global.exception.code

import org.springframework.http.HttpStatus

interface ErrorCode {
    val errorName: String
    val status: HttpStatus
    var bodyMessage: String
}
