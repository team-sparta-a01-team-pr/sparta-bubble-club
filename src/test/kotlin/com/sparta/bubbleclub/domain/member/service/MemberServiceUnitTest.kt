package com.sparta.bubbleclub.domain.member.service

import com.sparta.bubbleclub.domain.member.dto.LoginRequest
import com.sparta.bubbleclub.domain.member.dto.SignupRequest
import com.sparta.bubbleclub.domain.member.entity.Member
import com.sparta.bubbleclub.domain.member.repository.MemberRepository
import com.sparta.bubbleclub.global.exception.common.CustomIllegalArgumentException
import com.sparta.bubbleclub.global.exception.member.DuplicateArgumentException
import com.sparta.bubbleclub.infra.jwt.JwtProperties
import com.sparta.bubbleclub.infra.jwt.JwtUtil
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class MemberServiceUnitTest : BehaviorSpec({

    val memberRepository: MemberRepository = mockk<MemberRepository>()
    val passwordEncoder = BCryptPasswordEncoder()
    val jwtProperty = JwtProperties(
        issuer = "test",
        secret = "12345678901234567890123456789012",
        accessTokenExpirationHour = 1,
    )
    val jwtUtil = JwtUtil(jwtProperty)
    val memberService = MemberService(memberRepository, passwordEncoder, jwtUtil)

    val nonExistingEmail = "test@test.com"
    val existingEmail = "existing@test.com"
    val password = "abcd2#abcd"
    val differentPassword = "qwer2#qwer"
    val nickname = "nickname"

    Given("회원 가입 시 비밀번호와 비밀번호 확인을 다르게 입력하고") {
        val failSignupRequest = SignupRequest(nonExistingEmail, password, differentPassword, nickname)

        Then("회원 가입을 시도하면 회원 가입에 실패하고 \"비밀번호가 일치하지 않습니다.\" 메시지를 받게 된다.") {
            val exception = shouldThrowExactly<CustomIllegalArgumentException> {
                memberService.signup(failSignupRequest)
            }
            exception.message shouldBe "비밀번호가 일치하지 않습니다."
        }
    }

    Given("회원 가입 시 이미 회원으로 등록되어 있는 이메일을 입력하고") {
        val failSignupRequest = SignupRequest(existingEmail, password, password, nickname)
        every { memberRepository.existsByEmail(existingEmail) } returns true

        Then("회원 가입을 시도하면 회원 가입에 실패하고 \"이미 사용 중인 이메일입니다.\" 메시지를 받게 된다.") {
            val exception = shouldThrowExactly<DuplicateArgumentException> {
                memberService.signup(failSignupRequest)
            }
            exception.message shouldBe "이미 사용 중인 이메일입니다."
        }
    }

    Given("회원 가입 가능한 회원 정보를 입력하고") {
        val successSignupRequest = SignupRequest(nonExistingEmail, password, password, nickname)
        every { memberRepository.existsByEmail(nonExistingEmail) } returns false
        every { memberRepository.save(any()) } returns Member(password, nonExistingEmail, nickname) /* 작성하기 애매한 부분 */

        Then("회원 가입을 시도하면 회원 가입에 성공한다.") {
            shouldNotThrowAny {
                memberService.signup(successSignupRequest)
            }
        }
    }

    Given("존재하지 않는 이메일을 입력하고") {
        val loginRequestWithNonExistingEmail = LoginRequest(nonExistingEmail, password)
        every { memberRepository.findByEmail(nonExistingEmail) } returns null

        Then("로그인을 시도하면 로그인에 실패하고 \"일치 회원 없음\" 메시지를 받게 된다.") {
            val exception = shouldThrowExactly<CustomIllegalArgumentException> {
                memberService.login(loginRequestWithNonExistingEmail)
            }
            exception.message shouldBe "일치 회원 없음"
        }
    }

    Given("존재하는 이메일을 입력했지만 일치하지 않은 비밀번호를 입력하고") {
        val loginRequestWithDifferentPassword = LoginRequest(existingEmail, differentPassword)
        val existingMember = Member(password, existingEmail, nickname)
        every { memberRepository.findByEmail(existingEmail) } returns existingMember

        Then("로그인을 시도하면 로그인에 실패하고 \"일치 회원 없음\" 메시지를 받게 된다.") {
            val exception = shouldThrowExactly<CustomIllegalArgumentException> {
                memberService.login(loginRequestWithDifferentPassword)
            }
            exception.message shouldBe "일치 회원 없음"
        }
    }
})
