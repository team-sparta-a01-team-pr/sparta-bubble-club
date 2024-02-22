package com.sparta.bubbleclub.domain.bubble.repository

import com.sparta.bubbleclub.fixture.bubble.BubbleFixture
import com.sparta.bubbleclub.fixture.member.MemberFixture
import com.sparta.bubbleclub.global.TestQueryDslConfig
import io.kotest.core.spec.style.BehaviorSpec
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
    private val entityManager: TestEntityManager
) : BehaviorSpec({

    val newBubble = BubbleFixture.entity
    val memberOfBubble = MemberFixture.member

    // 버블 등록 ?
    Given("버블 데이터가 없을 때") {
        When("getBubbles 를 실행하면") {
            Then("아무것도 안 나온다.") {
            }
        }
    }

    Given("searchBubbles") {
        When("dddfd") {
            Then("dfd")
        }
    }

})
