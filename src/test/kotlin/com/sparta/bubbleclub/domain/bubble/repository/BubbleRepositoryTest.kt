package com.sparta.bubbleclub.domain.bubble.repository

import com.sparta.bubbleclub.domain.member.repository.MemberRepository
import com.sparta.bubbleclub.fixture.bubble.BubbleFixture
import com.sparta.bubbleclub.fixture.common.CommonFixture
import com.sparta.bubbleclub.global.TestQueryDslConfig
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource


@DataJpaTest
@ActiveProfiles("test")
@Import(value = [TestQueryDslConfig::class])
@TestPropertySource("classpath:application-test.yml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BubbleRepositoryTest(
    private val bubbleRepository: BubbleRepository,
    private val memberRepository: MemberRepository,
    private val entityManager: TestEntityManager
) : BehaviorSpec({

    extension(SpringTestExtension(SpringTestLifecycleMode.Root))

    Given("전체 데이터 조회를 위한 getBubbles를 호출할 때") {

        When("버블 데이터가 없으면") {
            val result = bubbleRepository.getBubbles(null, CommonFixture.firstPage)

            Then("emptyList가 반환된다.") {
                result.content shouldBe emptyList()
            }
        }

        When("버블 데이터가 있으면") {
            val list = BubbleFixture.bubbleList
            println(list)

            bubbleRepository.saveAll(list)
            entityManager.flush()

            val result = bubbleRepository.getBubbles(null, CommonFixture.firstPage)

            Then("올바른 Slice가 반환된다.") {
                println(result.content)
                result.content.size shouldBe list.size
            }
        }
    }

    Given("searchBubbles를 호출할 때") {

        When("키워드가 포함된 데이터가 없을 때") {
            val result = bubbleRepository.searchBubbles(
                BubbleFixture.bubbleId,
                BubbleFixture.randomKeyword,
                CommonFixture.firstPage
            )

            Then("emptyList가 반환된다.") {
                result.content shouldBe emptyList()
            }
        }

        When("키워드가 포함된 데이터가 있을 때") {
            val list = BubbleFixture.listForSearch

            bubbleRepository.saveAll(list)
            entityManager.flush()

            val result = bubbleRepository.searchBubbles(null, BubbleFixture.fixedKeyword, CommonFixture.firstPage)
            Then("키워드로 시작하는 단어가 포함된 리스트가 반환된다.") {
                result.content.map {
                    it!!.content shouldStartWith BubbleFixture.fixedKeyword
                }
            }
        }
    }

})
