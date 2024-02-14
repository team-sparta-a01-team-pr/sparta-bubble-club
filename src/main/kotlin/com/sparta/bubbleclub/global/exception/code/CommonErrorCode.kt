package com.sparta.bubbleclub.global.exception.code

import org.springframework.http.HttpStatus

enum class CommonErrorCode(
    val status: HttpStatus,
    val bodyMessage: String
) : ErrorCode {

    ENTITY_NOT_FOUND_ERR(HttpStatus.NOT_FOUND, "존재하지 않는 Entity 입니다"),
    INTERNAL_SERVER_ERR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 서버 에러가 발생하였습니다."),
    REQUEST_INVALID_ERR(HttpStatus.BAD_REQUEST, "조건에 맞지 않는 입력값입니다.");

    override fun errorName() = name
    override fun status() = status
    override fun bodyMessage() = bodyMessage

}
