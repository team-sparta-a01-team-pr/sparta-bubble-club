package com.sparta.bubbleclub.domain.bubble.repository

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.sparta.bubbleclub.domain.bubble.dto.response.BubbleResponse
import com.sparta.bubbleclub.domain.bubble.entity.QBubble
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Repository

@Repository
class BubbleQueryDslRepositoryImpl(
    val queryFactory: JPAQueryFactory
) : BubbleQueryDslRepository {

    private val bubble: QBubble = QBubble.bubble

    // 커서기반 페이징, keyword like 조건 (keyword 인기 검색어 )
    override fun getBubblesByKeyword(bubbleId: Long?, keyword: String?, pageable: Pageable): Slice<BubbleResponse> {
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
                bubble.content.like("$keyword%")
            )
            .orderBy(bubble.id.desc())
            .limit(pageSize + 1)
            .fetch()

        return checkLastPage(pageable, result)
    }

    // id 보다 작은 bubbles
    private fun ltBubbleId(bubbleId: Long?): BooleanExpression? {
        if (bubbleId == null) {
            return null
        }

        return bubble.id.lt(bubbleId)
    }

    // 마지막 페이지 확인
    private fun checkLastPage(pageable: Pageable, result: MutableList<BubbleResponse>): Slice<BubbleResponse> {

        var hasNext = false
        if(result.size > pageable.pageSize ) {
            result.removeAt(result.size-1)
            hasNext = true
        }

        return SliceImpl<BubbleResponse>(result, pageable, hasNext)
    }

}