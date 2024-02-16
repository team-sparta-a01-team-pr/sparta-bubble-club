package com.sparta.bubbleclub.domain.bubble.service

import com.sparta.bubbleclub.domain.bubble.dto.request.CreateBubbleRequest
import com.sparta.bubbleclub.domain.bubble.dto.request.UpdateBubbleRequest
import com.sparta.bubbleclub.domain.bubble.dto.response.BubbleResponse
import com.sparta.bubbleclub.domain.bubble.repository.BubbleRepository
import com.sparta.bubbleclub.domain.keyword.service.KeywordService
import com.sparta.bubbleclub.domain.member.repository.MemberRepository
import com.sparta.bubbleclub.global.exception.common.NoSuchEntityException
import com.sparta.bubbleclub.global.security.web.dto.MemberPrincipal
import jakarta.transaction.Transactional
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BubbleService(
    private val bubbleRepository: BubbleRepository,
    private val memberRepository: MemberRepository,
    private val keywordService: KeywordService
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

    @Transactional
    fun searchBubbles(bubbleId: Long?, keyword: String?, pageable: Pageable): Slice<BubbleResponse>? {
        keyword?.let { keywordService.increaseKeywordCount(keyword) }
        return bubbleRepository.searchBubbles(bubbleId, keyword, pageable)
    }

    fun getBubbles(bubbleId: Long?, pageable: Pageable): Slice<BubbleResponse> {
        return bubbleRepository.getBubbles(bubbleId, pageable)
    }

    @Transactional
    @Cacheable(value = ["bubbles"], key = "#keyword", condition = "#bubbleId == null")
    fun searchBubblesWithCaching(bubbleId: Long?, keyword: String?, pageable: Pageable): Slice<BubbleResponse>? {
        keyword?.let { keywordService.increaseKeywordCount(keyword) }
        return bubbleRepository.searchBubbles(bubbleId, keyword, pageable)
    }

    @Cacheable(value = ["bubbles"], key = "''", condition = "#bubbleId == null")
    fun getBubblesWithCaching(bubbleId: Long?, pageable: Pageable): Slice<BubbleResponse> {
        return bubbleRepository.getBubbles(bubbleId, pageable)
    }

    @Transactional
    fun delete(bubbleId: Long) {
        val bubble = getByIdOrNull(bubbleId)

        bubbleRepository.delete(bubble)
    }

    private fun getByIdOrNull(bubbleId: Long) =
        bubbleRepository.findByIdOrNull(bubbleId) ?: throw NoSuchEntityException(errorMessage = "해당 Bubble은 존재하지 않습니다.")
}