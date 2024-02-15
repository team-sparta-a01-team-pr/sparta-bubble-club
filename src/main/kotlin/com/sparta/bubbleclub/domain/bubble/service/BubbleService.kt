package com.sparta.bubbleclub.domain.bubble.service

import com.sparta.bubbleclub.domain.bubble.dto.request.CreateBubbleRequest
import com.sparta.bubbleclub.domain.bubble.dto.request.UpdateBubbleRequest
import com.sparta.bubbleclub.domain.bubble.repository.BubbleRepository
import com.sparta.bubbleclub.global.exception.common.NoSuchEntityException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BubbleService(
    private val bubbleRepository: BubbleRepository
) {
    @Transactional
    fun save(request: CreateBubbleRequest): Long {
        val bubble = request.toEntity()

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
    fun delete(bubbleId: Long) {
        val bubble = getByIdOrNull(bubbleId)

        bubbleRepository.delete(bubble)
    }

    private fun getByIdOrNull(bubbleId: Long) =
        bubbleRepository.findByIdOrNull(bubbleId) ?: throw NoSuchEntityException(errorMessage = "해당 Bubble은 존재하지 않습니다.")
}