package com.sparta.bubbleclub.domain.bubble.dto.request

import com.sparta.bubbleclub.domain.keyword.entity.KeywordStore
import jakarta.validation.constraints.NotBlank
import java.time.ZonedDateTime

data class SearchKeywordRequest(
    @NotBlank(message = "공백이 아닌 검색어를 입력해주세요") val keyword: String,
) {

    fun toEntity() = KeywordStore(this.keyword, count = 0L, createdAt = ZonedDateTime.now())
}
