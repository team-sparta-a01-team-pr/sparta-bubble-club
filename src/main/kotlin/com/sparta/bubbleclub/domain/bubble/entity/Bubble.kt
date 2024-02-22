package com.sparta.bubbleclub.domain.bubble.entity

import com.sparta.bubbleclub.domain.bubble.dto.request.UpdateBubbleRequest
import com.sparta.bubbleclub.domain.member.entity.Member
import com.sparta.bubbleclub.global.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "bubbles")
class Bubble(
    content: String,
    member: Member
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bubble_id")
    var id: Long? = null
        private set

    @Column(name = "content")
    var content: String = content
        private set

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "member_id")
    var member: Member = member

    fun update(request: UpdateBubbleRequest) {
        content = request.content
    }
}
