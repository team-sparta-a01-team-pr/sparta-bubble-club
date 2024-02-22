package com.sparta.bubbleclub.global

import com.querydsl.jpa.impl.JPAQueryFactory
import com.sparta.bubbleclub.domain.bubble.repository.BubbleQueryDslRepositoryImpl
import com.sparta.bubbleclub.domain.keyword.repository.KeywordQueryDslRepositoryImpl
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class TestQueryDslConfig(
    @PersistenceContext
    val entityManager: EntityManager
) {

    @Bean
    fun jpaQueryFactory(): JPAQueryFactory {
        return JPAQueryFactory(entityManager)
    }

    @Bean
    fun keywordQueryDslRepositoryImpl(): KeywordQueryDslRepositoryImpl {
        return KeywordQueryDslRepositoryImpl(jpaQueryFactory())
    }

    @Bean
    fun bubbleQueryDslRepositoryImpl(): BubbleQueryDslRepositoryImpl {
        return BubbleQueryDslRepositoryImpl(jpaQueryFactory())
    }

}
