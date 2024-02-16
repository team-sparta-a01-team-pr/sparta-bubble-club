package com.sparta.bubbleclub.domain.keyword.controller

import com.sparta.bubbleclub.domain.bubble.dto.response.BubbleResponse
import com.sparta.bubbleclub.domain.keyword.dto.response.KeywordResponse
import com.sparta.bubbleclub.domain.keyword.service.KeywordService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/keywords")
class KeywordController(
    private val keywordService: KeywordService
) {

    @GetMapping
    fun getPopularKeywords(): ResponseEntity<List<KeywordResponse>> {
        return ResponseEntity.ok().body(keywordService.getPopularKeywords())
    }
}