package com.sparta.bubbleclub.domain.bubble.dto.response

import java.io.Serializable
import java.time.ZonedDateTime

data class BubbleResponse(
    val id: Long,
    val content: String,
    val nickname: String,
    val createdAt: ZonedDateTime
): Serializable
