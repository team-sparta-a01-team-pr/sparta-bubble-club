package com.sparta.bubbleclub.domain.bubble.controller

import com.sparta.bubbleclub.domain.bubble.dto.request.CreateBubbleRequest
import com.sparta.bubbleclub.domain.bubble.dto.request.UpdateBubbleRequest
import com.sparta.bubbleclub.domain.bubble.dto.response.BubbleResponse
import com.sparta.bubbleclub.domain.bubble.service.BubbleService
import com.sparta.bubbleclub.global.security.web.dto.MemberPrincipal
import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/v1/bubbles")
class BubbleController(
    private val bubbleService: BubbleService
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
        @RequestBody @Valid request: UpdateBubbleRequest
    ): ResponseEntity<URI> {
        val id = bubbleService.update(bubbleId, request)

        return ResponseEntity.ok().body(URI.create(String.format("/api/v1/bubbles/%d", id)))
    }

    @GetMapping
    fun getBubbles(
        @RequestParam bubbleId: Long,
        @RequestParam(value = "keyword") keyword: String?,
        @PageableDefault(size = 10)  pageable: Pageable,
    ): ResponseEntity<Slice<BubbleResponse>> {
        return ResponseEntity.ok().body(bubbleService.getBubblesByKeyword(bubbleId, keyword, pageable))
    }

    @DeleteMapping("/{bubbleId}")
    fun deleteBubble(@PathVariable bubbleId: Long): ResponseEntity<Unit> {
        bubbleService.delete(bubbleId)

        return ResponseEntity.noContent().build()
    }
}