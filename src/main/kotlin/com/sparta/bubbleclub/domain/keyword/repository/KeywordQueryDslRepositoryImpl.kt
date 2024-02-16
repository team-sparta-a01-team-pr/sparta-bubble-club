package com.sparta.bubbleclub.domain.keyword.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.sparta.bubbleclub.domain.keyword.entity.QKeywordStore
import com.sparta.bubbleclub.domain.keyword.dto.response.KeywordResponse

class KeywordQueryDslRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : KeywordQueryDslRepository {

    private val keywordStore: QKeywordStore = QKeywordStore.keywordStore

    override fun getPopularKeywords(): List<KeywordResponse> {
        return queryFactory.selectFrom(keywordStore)
            .orderBy(keywordStore.count.desc())
            .limit(10)
            .fetch()
            .map { KeywordResponse.toResponse(it) }
    }
}