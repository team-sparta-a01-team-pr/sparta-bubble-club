package com.sparta.bubbleclub.domain.bubble.repository

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.sparta.bubbleclub.domain.bubble.dto.response.BubbleResponse
import com.sparta.bubbleclub.domain.bubble.dto.response.CustomSliceImpl
import com.sparta.bubbleclub.domain.bubble.entity.QBubble
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class BubbleQueryDslRepositoryImpl(
    val queryFactory: JPAQueryFactory
) : BubbleQueryDslRepository {

    private val bubble: QBubble = QBubble.bubble

    override fun getBubbles(bubbleId: Long?, pageable: Pageable): CustomSliceImpl<BubbleResponse> {
        val pageSize = pageable.pageSize.toLong()
        val result = queryFactory.select(
            Projections.constructor(
                BubbleResponse::class.java,
                bubble.id,
                bubble.content,
                bubble.member.nickname,
                bubble.createdAt
            )
        ).from(bubble)
            .innerJoin(bubble.member)
            .where(
                ltBubbleId(bubbleId)
            )
            .orderBy(bubble.id.desc())
            .limit(pageSize + 1)
            .fetch()

        return checkLastPage(result, pageable)
    }

    // 커서기반 페이징
    override fun searchBubbles(bubbleId: Long?, keyword: String?, pageable: Pageable): CustomSliceImpl<BubbleResponse> {
        val pageSize = pageable.pageSize.toLong()
        val result = queryFactory.select(
            Projections.constructor(
                BubbleResponse::class.java,
                bubble.id,
                bubble.content,
                bubble.member.nickname,
                bubble.createdAt
            )
        ).from(bubble)
            .innerJoin(bubble.member)
            .where(
                ltBubbleId(bubbleId),
                isNullKeyword(keyword)
            )
            .orderBy(bubble.id.desc())
            .limit(pageSize + 1)
            .fetch()

        return checkLastPage(result, pageable)
    }

    // id 보다 작은 bubbles
    private fun ltBubbleId(bubbleId: Long?): BooleanExpression? {
        if (bubbleId == null) {
            return null
        }

        return bubble.id.lt(bubbleId)
    }

    private fun isNullKeyword(keyword: String?): BooleanExpression? {
        if (keyword == null) {
            return null
        }

        return bubble.content.like("$keyword%")
    }

    // 마지막 페이지 확인
    private fun checkLastPage(result: MutableList<BubbleResponse>, pageable: Pageable): CustomSliceImpl<BubbleResponse> {

        var hasNext = false

        if(result.size > pageable.pageSize ) {
            result.removeAt(result.size-1)
            hasNext = true
        }
        return CustomSliceImpl<BubbleResponse>(result, pageable.pageSize, hasNext)
    }

}