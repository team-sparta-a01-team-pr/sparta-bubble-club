package com.sparta.bubbleclub.fixture.keyword

import com.appmattus.kotlinfixture.kotlinFixture
import com.sparta.bubbleclub.domain.bubble.dto.request.SearchKeywordRequest
import com.sparta.bubbleclub.domain.keyword.dto.response.KeywordResponse
import com.sparta.bubbleclub.domain.keyword.entity.KeywordStore
import java.time.ZonedDateTime

object KeywordFixture {

    val fixture = kotlinFixture()

    val searchKeywordRequest = fixture<SearchKeywordRequest>()

    val keywordStoresFromQueryDsl = fixture<List<KeywordStore>> {
        repeatCount { 15 }
    }

    val keywordStoresFromCache = fixture<List<KeywordStore>> {
        repeatCount { 10 }
    }

    val cacheResponse = fixture<Set<String>> {
        repeatCount { 10 }
    }

    val keywordResponseFromCache = fixture<List<KeywordResponse>> {
        repeatCount { 10 }
    }

    val keywordResponseFromRdb = fixture<List<KeywordResponse>> {
        repeatCount { 10 }
    }

    fun generate(keyword: String = fixture(), count: Long = fixture(), createdAt: ZonedDateTime = fixture()) = KeywordStore(keyword, count, createdAt)

}