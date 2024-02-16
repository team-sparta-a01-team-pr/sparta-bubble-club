package com.sparta.bubbleclub.domain.bubble.dto.request

import jakarta.validation.constraints.NotBlank

data class SearchKeywordRequest(
    @NotBlank(message = "공백이 아닌 검색어를 입력해주세요") val keyword: String,
)
