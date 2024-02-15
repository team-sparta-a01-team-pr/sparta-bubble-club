package com.sparta.bubbleclub.domain.bubble.dto.request

import jakarta.validation.constraints.NotBlank

data class UpdateBubbleRequest(
    @field:NotBlank(message = "내용은 공백일 수 없습니다.")
    val content: String
)