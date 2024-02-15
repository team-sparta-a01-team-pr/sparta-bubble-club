package com.sparta.bubbleclub.domain.member.dto

data class LoginRequest(
    val email: String,
    val password: String,
)
