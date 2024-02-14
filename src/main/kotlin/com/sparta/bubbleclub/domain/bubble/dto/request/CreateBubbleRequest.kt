package com.sparta.bubbleclub.domain.bubble.dto.request

import com.sparta.bubbleclub.domain.bubble.entity.Bubble
import com.sparta.bubbleclub.domain.member.entity.Member

data class CreateBubbleRequest(
    val content: String,
    val member: Member
) {

    fun toEntity() = Bubble(this.content, this.member)

}