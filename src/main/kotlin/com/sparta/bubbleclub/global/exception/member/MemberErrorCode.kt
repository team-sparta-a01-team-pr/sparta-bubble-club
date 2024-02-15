package com.sparta.bubbleclub.global.exception.member

import com.sparta.bubbleclub.global.exception.code.ErrorCode
import org.springframework.http.HttpStatus

enum class MemberErrorCode(
    override val status: HttpStatus,
    override var bodyMessage: String
) : ErrorCode {

    DUPLICATE_ARGS_ERR(HttpStatus.BAD_REQUEST, "중복되는 입력값이 존재합니다.");

    override val errorName = name
}