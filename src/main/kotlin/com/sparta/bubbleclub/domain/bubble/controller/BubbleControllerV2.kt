package com.sparta.bubbleclub.domain.bubble.controller

import com.sparta.bubbleclub.domain.bubble.dto.request.SearchKeywordRequest
import com.sparta.bubbleclub.domain.bubble.dto.response.BubbleResponse
import com.sparta.bubbleclub.domain.bubble.service.BubbleService
import jakarta.validation.Valid
import jakarta.validation.constraints.Size
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v2/bubbles")
class BubbleControllerV2(
    private val bubbleService: BubbleService
) {

    @GetMapping("/search")
    fun searchBubbles(
        @RequestParam bubbleId: Long?,
        @Valid request: SearchKeywordRequest
    ): ResponseEntity<Slice<BubbleResponse>> {
        return ResponseEntity.ok()
            .body(bubbleService.searchBubblesWithCaching(bubbleId, request.keyword, PageRequest.of(0, 10)))
    }

    @GetMapping
    fun getBubbles(
        @RequestParam bubbleId: Long?
    ): ResponseEntity<Slice<BubbleResponse>> {
        return ResponseEntity.ok().body(bubbleService.getBubblesWithCaching(bubbleId, PageRequest.of(0, 10)))
    }
}
