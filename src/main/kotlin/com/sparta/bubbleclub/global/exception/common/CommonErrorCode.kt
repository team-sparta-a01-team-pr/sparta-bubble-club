package com.sparta.bubbleclub.global.exception.common

import com.sparta.bubbleclub.global.exception.code.ErrorCode
import org.springframework.http.HttpStatus

enum class CommonErrorCode(
    override val status: HttpStatus,
    override var bodyMessage: String
) : ErrorCode {

    ENTITY_NOT_FOUND_ERR(HttpStatus.NOT_FOUND, "존재하지 않는 Entity 입니다"),
    INTERNAL_SERVER_ERR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 서버 에러가 발생하였습니다."),
    INVALID_ARGS_ERR(HttpStatus.BAD_REQUEST, "주어진 입력값이 올바르지 않습니다."),
    HAS_NO_PERMISSION(HttpStatus.FORBIDDEN, "권한이 존재하지 않습니다.")

    ;
    override val errorName: String = name
}
