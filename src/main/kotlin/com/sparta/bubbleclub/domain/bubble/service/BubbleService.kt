package com.sparta.bubbleclub.domain.bubble.service

import com.sparta.bubbleclub.domain.bubble.dto.request.CreateBubbleRequest
import com.sparta.bubbleclub.domain.bubble.dto.request.UpdateBubbleRequest
import com.sparta.bubbleclub.domain.bubble.dto.response.BubbleResponse
import com.sparta.bubbleclub.domain.bubble.repository.BubbleRepository
import com.sparta.bubbleclub.domain.member.repository.MemberRepository
import com.sparta.bubbleclub.global.exception.common.NoSuchEntityException
import com.sparta.bubbleclub.global.security.web.dto.MemberPrincipal
import jakarta.transaction.Transactional
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BubbleService(
    private val bubbleRepository: BubbleRepository,
    private val memberRepository: MemberRepository
) {
    @Transactional
    fun save(memberPrincipal: MemberPrincipal, request: CreateBubbleRequest): Long {
        val member = memberRepository.findByIdOrNull(memberPrincipal.id) ?: throw NoSuchEntityException(errorMessage = "해당 Member는 존재하지 않습니다.")
        val bubble = request.toEntity(member)

        bubbleRepository.save(bubble)

        return bubble.id!!
    }

    @Transactional
    fun update(bubbleId: Long, request: UpdateBubbleRequest): Long {
        val bubble = getByIdOrNull(bubbleId)

        bubble.update(request)

        return bubble.id!!
    }

    fun getBubblesByKeyword(bubbleId: Long, keyword: String?, pageable: Pageable): Slice<BubbleResponse> {
        return bubbleRepository.getBubblesByKeyword(bubbleId, keyword, pageable)
    }

    @Transactional
    fun delete(bubbleId: Long) {
        val bubble = getByIdOrNull(bubbleId)

        bubbleRepository.delete(bubble)
    }

    private fun getByIdOrNull(bubbleId: Long) =
        bubbleRepository.findByIdOrNull(bubbleId) ?: throw NoSuchEntityException(errorMessage = "해당 Bubble은 존재하지 않습니다.")
}