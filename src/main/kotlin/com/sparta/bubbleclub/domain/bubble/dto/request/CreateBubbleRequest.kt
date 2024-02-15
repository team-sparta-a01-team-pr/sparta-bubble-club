package com.sparta.bubbleclub.domain.bubble.dto.request

import com.sparta.bubbleclub.domain.bubble.entity.Bubble
import com.sparta.bubbleclub.domain.member.entity.Member
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateBubbleRequest(
    @field:NotBlank(message = "내용은 공백일 수 없습니다.")
    @field:Size(max = 300, message = "내용은 300자를 넘을 수 없습니다.")
    val content: String
) {

    fun toEntity(member: Member) = Bubble(
        content = this.content,
        member = member
    )

}