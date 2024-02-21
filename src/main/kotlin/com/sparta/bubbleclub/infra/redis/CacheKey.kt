package com.sparta.bubbleclub.infra.redis

object CacheKey {
    // 전체 조회
    const val GET_ALL_BUBBLES = "BUBBLES"
    // 키워드 검색 조회
    const val GET_BUBBLES_BY_KEYWORD = "KEYWORD"
    // 인기 검색어 목록
    const val POPULAR_KEYWORDS = "POPULARS"
}
