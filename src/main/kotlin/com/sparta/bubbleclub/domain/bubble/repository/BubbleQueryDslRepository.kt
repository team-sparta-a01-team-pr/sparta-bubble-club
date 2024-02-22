package com.sparta.bubbleclub.domain.bubble.repository

import com.sparta.bubbleclub.domain.bubble.dto.response.BubbleResponse
import com.sparta.bubbleclub.domain.bubble.dto.response.CustomSliceImpl
import org.springframework.data.domain.Pageable

interface BubbleQueryDslRepository {

    fun getBubbles(bubbleId: Long?, pageable: Pageable): CustomSliceImpl<BubbleResponse>
    fun searchBubbles(bubbleId: Long?, keyword: String?, pageable: Pageable): CustomSliceImpl<BubbleResponse>
}