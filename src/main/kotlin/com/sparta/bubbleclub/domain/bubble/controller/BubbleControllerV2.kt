package com.sparta.bubbleclub.domain.bubble.controller

import com.sparta.bubbleclub.domain.bubble.dto.response.BubbleResponse
import com.sparta.bubbleclub.domain.bubble.service.BubbleService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v2/bubbles")
class BubbleControllerV2(
    private val bubbleService: BubbleService
) {

    @GetMapping
    fun getBubbles(
        @RequestParam bubbleId: Long?,
        @RequestParam(value = "keyword") keyword: String?
    ): ResponseEntity<Slice<BubbleResponse>> {
        return ResponseEntity.ok().body(bubbleService.getBubblesByKeywordWithCaching(bubbleId, keyword, PageRequest.of(0, 10)))
    }
}
