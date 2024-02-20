package com.sparta.bubbleclub.domain.member.dto

import com.sparta.bubbleclub.domain.member.entity.Member
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class SignupRequest(
    @field:Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "올바른 이메일 형식을 입력해주세요")
    val email: String,

    @field:Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).{8,15}$",
        message = "비밀번호는 8자 이상의 문자열, 하나 이상 숫자, 문자, 특수문자를 포함해주세요"
    )
    val password: String,

    @field:Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).{8,15}$",
        message = "비밀번호는 8자 이상의 문자열, 하나 이상 숫자, 문자, 특수문자를 포함해주세요"
    )
    val passwordConfirm: String,

    @field:NotBlank(message = "닉네임은 필수 입력 사항입니다.")
    val nickname: String
)