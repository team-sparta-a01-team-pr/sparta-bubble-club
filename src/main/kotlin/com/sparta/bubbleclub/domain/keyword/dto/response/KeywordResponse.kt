package com.sparta.bubbleclub.domain.keyword.dto.response

import com.sparta.bubbleclub.domain.keyword.entity.KeywordStore

data class KeywordResponse(
    val keyword: String
) {
    companion object {
        fun toResponse(keywordStore: KeywordStore) = KeywordResponse(keyword = keywordStore.keyword)
    }
}
