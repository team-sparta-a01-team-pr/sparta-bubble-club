package com.sparta.bubbleclub.domain.keyword.service

import com.sparta.bubbleclub.domain.bubble.dto.request.SearchKeywordRequest
import com.sparta.bubbleclub.domain.keyword.dto.response.KeywordResponse
import com.sparta.bubbleclub.domain.keyword.entity.KeywordStore
import com.sparta.bubbleclub.domain.keyword.repository.KeywordRepository
import jakarta.transaction.Transactional
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class KeywordService(
    private val keywordRepository: KeywordRepository,
    private val redisTemplate: RedisTemplate<String, String>,
) {

    private val zSetOperations = redisTemplate.opsForZSet()

    companion object {
        private const val REDIS_KEY = "keyword"
    }

    @Transactional
    fun increaseKeywordCount(request: SearchKeywordRequest) {
        // redis에 이 keyword 있는지 검사, 없으면 추가
        zSetOperations.addIfAbsent(REDIS_KEY, request.keyword, 0.0)

        // score ++
        increaseScore(request.keyword)
    }

    fun getPopularKeywords(): List<KeywordResponse> {
        val rank = zSetOperations.reverseRange(REDIS_KEY, 0, 9) as Set<String>?
        return rank?.map { KeywordResponse.toResponse(it) } ?: keywordRepository.getPopularKeywords()
    }

    @Transactional
    @Scheduled(fixedDelay = 1000 * 60 * 10)
    fun flushAllCache() {
        zSetOperations.reverseRangeWithScores(REDIS_KEY, 0, 9)
            .let {
                it?.map { keyword -> KeywordStore(keyword.value!!, keyword.score!!.toLong(), ZonedDateTime.now()) }
            }
            ?.also { keywords -> keywordRepository.saveAll(keywords) }

        zSetOperations.removeRange(REDIS_KEY, 0, zSetOperations.size(REDIS_KEY)?.minus(1) ?: 0)
    }

    private fun increaseScore(keyword: String) {
        zSetOperations.incrementScore(REDIS_KEY, keyword, 1.0)
    }

}
