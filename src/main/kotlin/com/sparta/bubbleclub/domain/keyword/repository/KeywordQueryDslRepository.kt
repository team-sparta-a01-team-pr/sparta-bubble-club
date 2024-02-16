package com.sparta.bubbleclub.domain.keyword.repository

import com.sparta.bubbleclub.domain.keyword.dto.response.KeywordResponse

interface KeywordQueryDslRepository {
    fun getPopularKeywords(): List<KeywordResponse>
}