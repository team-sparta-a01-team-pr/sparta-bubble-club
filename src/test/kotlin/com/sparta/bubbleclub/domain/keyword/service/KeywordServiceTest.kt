package com.sparta.bubbleclub.domain.keyword.service

import com.sparta.bubbleclub.domain.keyword.repository.KeywordRepository
import com.sparta.bubbleclub.fixture.keyword.KeywordFixture
import com.sparta.bubbleclub.infra.redis.CacheKey
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.springframework.data.redis.core.ZSetOperations

class KeywordServiceTest(
    private val keywordService: KeywordService = mockk<KeywordService>(),
    private val keywordRepository: KeywordRepository = mockk<KeywordRepository>()
) : BehaviorSpec({

    val sortedSetOperation = mockk<ZSetOperations<String, String>>()

    Given("검색어가 주어졌을 때") {

        When("캐시에 저장되어 있지 않다면") {
            every { keywordService.increaseKeywordCount(any()) } just runs
            every { sortedSetOperation.addIfAbsent(any(), any(), 0.0) } returns true
            every { sortedSetOperation.incrementScore(any(), any(), 1.0) } returns 1.0

            val savedResult = sortedSetOperation.addIfAbsent(
                CacheKey.POPULAR_KEYWORDS,
                KeywordFixture.searchKeywordRequest.keyword,
                0.0
            )
            val increaseResult = sortedSetOperation.incrementScore(
                CacheKey.POPULAR_KEYWORDS,
                KeywordFixture.searchKeywordRequest.keyword,
                1.0
            )

            Then("캐시에 저장하고 [Key : Value]의 스코어를 올린다.") {
                savedResult shouldBe true
                increaseResult shouldBe 1.0
            }
        }

        When("캐시에 저장되어 있다면") {
            every { sortedSetOperation.incrementScore(any(), any(), 1.0) } returns 1.0

            val increaseResult = sortedSetOperation.incrementScore(
                CacheKey.POPULAR_KEYWORDS,
                KeywordFixture.searchKeywordRequest.keyword,
                1.0
            )

            Then("[Key : Value]의 스코어를 올린다") {
                increaseResult shouldBe 1.0
            }
        }
    }

    Given("인기 검색어 목록을 조회할 때") {

        When("캐시에 목록이 있으면") {
            every { keywordService.getPopularKeywords() } returns KeywordFixture.keywordResponseFromCache
            every { sortedSetOperation.reverseRange(any(), 0, 9) } returns KeywordFixture.cacheResponse

            val cacheExecuteResult = sortedSetOperation.reverseRange(CacheKey.POPULAR_KEYWORDS, 0, 9) as Set<String>?
            val serviceExecuteResult = keywordService.getPopularKeywords()

            Then("10개의 캐시 데이터를 반환한다.") {
                cacheExecuteResult!!.size shouldBe 10
                serviceExecuteResult.size shouldBe 10
            }
        }

        When("캐시에 목록이 없으면") {
            every { keywordService.getPopularKeywords() } returns KeywordFixture.keywordResponseFromRdb

            val result = keywordService.getPopularKeywords()

            Then("RDB에서 목록을 조회해 10개의 데이터를 반환한다.") {
                result.size shouldBe 10
            }
        }
    }

    Given("애플리케이션이 시작되고, 10분이 지났을 때") {
        When("캐시 키 중 \"POPULARS\"에 값이 존재한다면") {
            every { keywordRepository.saveAll(KeywordFixture.keywordStoresFromCache) } returns KeywordFixture.keywordStoresFromCache
            every { sortedSetOperation.removeRange(CacheKey.POPULAR_KEYWORDS, any(), any()) } returns null

            val savedResult = keywordRepository.saveAll(KeywordFixture.keywordStoresFromCache)
            val cacheResult = sortedSetOperation.removeRange(
                CacheKey.POPULAR_KEYWORDS,
                0,
                9
            )

            Then("10분을 주기로 DB에 저장한 뒤, 캐시의 모든 값을 Flush 한다.") {
                savedResult.size shouldBe 10
                cacheResult shouldBe null
            }
        }
    }

})
