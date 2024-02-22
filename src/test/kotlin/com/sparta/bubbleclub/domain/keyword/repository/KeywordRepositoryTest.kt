package com.sparta.bubbleclub.domain.keyword.repository

import com.sparta.bubbleclub.fixture.keyword.KeywordFixture
import com.sparta.bubbleclub.global.TestQueryDslConfig
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.ints.shouldBeLessThanOrEqual
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource

@DataJpaTest
@ActiveProfiles("test")
@Import(value = [TestQueryDslConfig::class])
@TestPropertySource("classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class KeywordRepositoryTest(
    private val keywordRepository: KeywordRepository
) : BehaviorSpec({

    Given("15개의 검색어가 목록에 있을 때") {
        val keywordList = KeywordFixture.keywordList
        keywordRepository.saveAll(keywordList)

        val sortedList = keywordList.sortedByDescending { it.count }

        When("인기 검색어 목록을 조회하면") {
            val result = keywordRepository.getPopularKeywords()

            Then("10개 이하의 인기 검색어가 count 높은 순으로 정렬되어 반환된다") {
                result.size shouldBeLessThanOrEqual 10
                result.zip(sortedList) { keywordResponse, keywordStore -> keywordResponse.keyword shouldBe keywordStore.keyword }
            }
        }
    }
})
