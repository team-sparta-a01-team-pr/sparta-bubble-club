package com.sparta.bubbleclub.domain.bubble.repository

import com.sparta.bubbleclub.domain.bubble.dto.response.BubbleResponse
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface BubbleQueryDslRepository {
    fun getBubblesByKeyword(bubbleId: Long?, keyword: String?, pageable: Pageable): Slice<BubbleResponse>
}