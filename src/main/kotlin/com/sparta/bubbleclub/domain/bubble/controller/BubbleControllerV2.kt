package com.sparta.bubbleclub.domain.bubble.controller

import com.sparta.bubbleclub.domain.bubble.dto.request.SearchKeywordRequest
import com.sparta.bubbleclub.domain.bubble.dto.response.BubbleResponse
import com.sparta.bubbleclub.domain.bubble.service.BubbleService
import com.sparta.bubbleclub.domain.keyword.service.KeywordService
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v2/bubbles")
class BubbleControllerV2(
    private val bubbleService: BubbleService,
    private val keywordService: KeywordService
) {

    @GetMapping("/search")
    fun searchBubbles(
        @RequestParam bubbleId: Long?,
        @Valid request: SearchKeywordRequest
    ): ResponseEntity<Slice<BubbleResponse>> {
        keywordService.increaseKeywordCount(request)
        val response = bubbleService.searchBubblesWithCaching(bubbleId, request.keyword, PageRequest.of(0, 10))
        return ResponseEntity.ok().body(response)
    }

    @GetMapping
    fun getBubbles(
        @RequestParam bubbleId: Long?
    ): ResponseEntity<Slice<BubbleResponse>> {
        val response = bubbleService.getBubblesWithCaching(bubbleId, PageRequest.of(0, 10))
        return ResponseEntity.ok().body(response)
    }
}
