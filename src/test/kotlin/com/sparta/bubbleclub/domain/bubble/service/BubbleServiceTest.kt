package com.sparta.bubbleclub.domain.bubble.service

import com.sparta.bubbleclub.fixture.bubble.BubbleFixture
import com.sparta.bubbleclub.fixture.common.CommonFixture
import com.sparta.bubbleclub.global.exception.common.CommonErrorCode
import com.sparta.bubbleclub.global.exception.common.HasNoPermissionException
import com.sparta.bubbleclub.global.exception.common.NoSuchEntityException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.redis.cache.RedisCacheManager

class BubbleServiceTest(
    private val bubbleService: BubbleService = mockk<BubbleService>(),
    private val redisCacheManager: RedisCacheManager = mockk<RedisCacheManager>()
) : BehaviorSpec({

    Given("Bubble을 저장할 때") {

        When("가입된 멤버라면") {
            every { bubbleService.save(any(), any()) } returns 1
            val id = bubbleService.save(CommonFixture.memberPrincipal, BubbleFixture.createRequest)

            Then("전체 조회와 키워드 검색 Cache 값이 사라지고, 버블이 저장된다.") {
                id shouldBe 1
            }
        }

        When("가입되지 않은 멤버라면") {
            every { bubbleService.save(any(), any()) } throws NoSuchEntityException(errorMessage = "해당 Member는 존재하지 않습니다.")

            val resultException = shouldThrowExactly<NoSuchEntityException> {
                bubbleService.save(CommonFixture.memberPrincipal, BubbleFixture.createRequest)
            }

            Then("NoSuchEntityException 이 발생한다.") {
                resultException.message shouldBe "해당 Member는 존재하지 않습니다."
            }
        }
    }

    Given("Bubble을 수정할 때") {

        When("버블이 존재하고 버블을 등록했던 멤버와 id가 같으면") {
            every { bubbleService.update(any(), any(), any()) } returns 1

            val id = bubbleService.update(1L, BubbleFixture.updateRequest, CommonFixture.memberPrincipal)

            Then("버블이 수정된다.") {
                id shouldBe 1
            }
        }

        When("버블을 등록했던 멤버와 id가 같지 않으면") {
            every { bubbleService.update(any(), any(), any()) } throws HasNoPermissionException()

            val resultException = shouldThrowExactly<HasNoPermissionException> {
                bubbleService.update(1L, BubbleFixture.updateRequest, CommonFixture.memberPrincipal)
            }

            Then("HasNoPermissionException 이 발생한다.") {
                resultException.errorMessage shouldBe CommonErrorCode.HAS_NO_PERMISSION.bodyMessage
            }
        }

        When("bubbleId로 조회한 버블이 존재하지 않으면") {
            every {
                bubbleService.update(any(), any(), any()) } throws NoSuchEntityException(errorMessage = "해당 Bubble은 존재하지 않습니다.")

            val resultException = shouldThrowExactly<NoSuchEntityException> {
                bubbleService.update(1L, BubbleFixture.updateRequest, CommonFixture.memberPrincipal)
            }

            Then("NoSuchEntityException 이 발생한다.") {
                resultException.errorMessage shouldBe "해당 Bubble은 존재하지 않습니다."
            }
        }
    }

    Given("버블을 전체 조회할 때") {
        When("bubbleId를 입력하지 않으면") {
            every { bubbleService.getBubbles(any(), any()) } returns BubbleFixture.bubbleFirstSlice

            val result = bubbleService.getBubbles(null, CommonFixture.firstPage)

            Then("첫 페이지가 반환된다.") {
                result!!.content[0].id shouldBe BubbleFixture.firstBubbleId
            }
        }

        When("bubbleId를 입력하면") {
            every { bubbleService.getBubbles(any(), any()) } returns BubbleFixture.bubbleNonFirstSlice

            val result = bubbleService.getBubbles(null, CommonFixture.firstPage)

            Then("bubbleId에 해당하는 페이지가 반환된다.") {
                result!!.content[0].id shouldNotBe BubbleFixture.firstBubbleId
            }
        }
    }

    Given("특정 키워드로 버블을 조회할 때") {
        When("bubbleId 를 입력하고, 키워드에 해당하는 버블이 존재하면") {
            every { bubbleService.searchBubbles(any(), any(), any()) } returns BubbleFixture.bubbleNonFirstSlice

            val result = bubbleService.searchBubbles(1L, BubbleFixture.fixedKeyword, CommonFixture.nonFirstPage)

            Then("키워드를 포함한 버블 최신 목록의 bubbleId에 해당하는 페이지가 반환된다.") {
                result!!.content[0].id shouldNotBe BubbleFixture.firstBubbleId
            }
        }

        When("bubbleId 를 입력하고, 키워드에 해당하는 버블이 존재하지 않으면") {
            every { bubbleService.searchBubbles(any(), any(), any()) } returns BubbleFixture.SliceWithoutBubbles

            val result = bubbleService.searchBubbles(1L, BubbleFixture.randomKeyword, CommonFixture.nonFirstPage)

            Then("빈 페이지가 반환된다.") {
                result!!.content shouldBe emptyList()
            }
        }

        When("bubbleId를 입력하지 않고, 키워드에 해당하는 버블이 존재하지 않으면") {
            every { bubbleService.searchBubbles(null, any(), any()) } returns BubbleFixture.SliceWithoutBubbles
            val result = bubbleService.searchBubbles(null, BubbleFixture.fixedKeyword, CommonFixture.firstPage)

            Then("빈 페이지가 반환된다.") {
                result!!.content shouldBe emptyList()
            }
        }

        When("bubbleId를 입력하지 않고, 키워드에 해당하는 버블이 존재하면") {
            every { bubbleService.searchBubbles(any(), any(), any()) } returns BubbleFixture.bubbleFirstSlice

            val result = bubbleService.searchBubbles(null, BubbleFixture.fixedKeyword, CommonFixture.firstPage)

            Then("키워드를 포함한 버블 최신 목록의 해당 키워드의 첫페이지가 반환된다.") {
                result!!.content[0].id shouldBe BubbleFixture.firstBubbleId
                result!!.content[0].content shouldStartWith BubbleFixture.fixedKeyword
            }

        }
    }

    Given("버블을 삭제할 때") {

        When("버블이 존재하고 버블을 등록했던 멤버와 id가 같으면") {
            every { bubbleService.delete(any(), any()) } returns Unit
            every { redisCacheManager.cacheNames.size } returns 0

            val result = bubbleService.delete(1L, CommonFixture.memberPrincipal)
            val cacheSize = redisCacheManager.cacheNames.size

            Then("전체 조회와 키워드 검색 Cache 값이 사라지고, 버블이 삭제된다.") {
                cacheSize shouldBe 0
                result shouldBe Unit
            }
        }

        When("버블을 등록했던 멤버와 id가 같지 않으면") {
            every { bubbleService.delete(any(), any()) } throws HasNoPermissionException()

            val resultException = shouldThrowExactly<HasNoPermissionException> {
                bubbleService.delete(1L, CommonFixture.memberPrincipal)
            }

            Then("HasNoPermissionException 이 발생한다.") {
                resultException.errorMessage shouldBe CommonErrorCode.HAS_NO_PERMISSION.bodyMessage
            }
        }

        When("bubbleId로 조회한 버블이 존재하지 않으면") {
            every {
                bubbleService.delete(
                    any(),
                    any()
                )
            } throws NoSuchEntityException(errorMessage = "해당 Bubble은 존재하지 않습니다.")

            val resultException = shouldThrow<NoSuchEntityException> {
                bubbleService.delete(1L, CommonFixture.memberPrincipal)
            }

            Then("NoSuchEntityException 이 발생한다.") {
                resultException.message shouldBe "해당 Bubble은 존재하지 않습니다."
            }
        }
    }
})
