package com.sparta.bubbleclub.domain.member.dto

data class SignupRequest(
    val email: String,
    val password: String,
    val passwordConfirm: String,
    val nickname: String
)