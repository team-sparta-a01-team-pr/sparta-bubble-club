package com.sparta.bubbleclub.domain.bubble.controller

import com.sparta.bubbleclub.domain.bubble.dto.request.CreateBubbleRequest
import com.sparta.bubbleclub.domain.bubble.dto.request.SearchKeywordRequest
import com.sparta.bubbleclub.domain.bubble.dto.request.UpdateBubbleRequest
import com.sparta.bubbleclub.domain.bubble.dto.response.BubbleResponse
import com.sparta.bubbleclub.domain.bubble.service.BubbleService
import com.sparta.bubbleclub.domain.keyword.service.KeywordService
import com.sparta.bubbleclub.global.security.web.dto.MemberPrincipal
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/v1/bubbles")
class BubbleController(
    private val bubbleService: BubbleService,
    private val keywordService: KeywordService
) {

    @PostMapping
    fun createBubble(
        @AuthenticationPrincipal memberPrincipal: MemberPrincipal,
        @RequestBody @Valid request: CreateBubbleRequest
    ): ResponseEntity<Unit> {
        val id = bubbleService.save(memberPrincipal, request)

        return ResponseEntity.created(URI.create(String.format("/api/v1/bubbles/%d", id))).build()
    }


    @PatchMapping("/{bubbleId}")
    fun updateBubble(
        @PathVariable bubbleId: Long,
        @RequestBody @Valid request: UpdateBubbleRequest,
        @AuthenticationPrincipal memberPrincipal: MemberPrincipal,
    ): ResponseEntity<URI> {
        val id = bubbleService.update(bubbleId, request, memberPrincipal)

        return ResponseEntity.ok().body(URI.create(String.format("/api/v1/bubbles/%d", id)))
    }

    @GetMapping("/search")
    fun searchBubbles(
        @RequestParam bubbleId: Long?,
        @Valid request: SearchKeywordRequest
    ): ResponseEntity<Slice<BubbleResponse>> {
        keywordService.increaseKeywordCount(request)
        val response = bubbleService.searchBubbles(bubbleId, request.keyword, PageRequest.of(0, 10))
        return ResponseEntity.ok().body(response)
    }

    @GetMapping
    fun getBubbles(
        @RequestParam bubbleId: Long?
    ): ResponseEntity<Slice<BubbleResponse>> {
        val response = bubbleService.getBubbles(bubbleId, PageRequest.of(0, 10))
        return ResponseEntity.ok().body(response)
    }

    @DeleteMapping("/{bubbleId}")
    fun deleteBubble(
        @PathVariable bubbleId: Long,
        @AuthenticationPrincipal memberPrincipal: MemberPrincipal
    ): ResponseEntity<Unit> {
        bubbleService.delete(bubbleId, memberPrincipal)

        return ResponseEntity.noContent().build()
    }
}