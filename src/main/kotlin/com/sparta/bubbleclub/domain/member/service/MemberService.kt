package com.sparta.bubbleclub.domain.member.service

import com.sparta.bubbleclub.domain.member.dto.LoginRequest
import com.sparta.bubbleclub.domain.member.dto.LoginResponse
import com.sparta.bubbleclub.domain.member.dto.SignupRequest
import com.sparta.bubbleclub.domain.member.entity.Member
import com.sparta.bubbleclub.domain.member.repository.MemberRepository
import com.sparta.bubbleclub.global.exception.common.CustomIllegalArgumentException
import com.sparta.bubbleclub.global.exception.member.DuplicateArgumentException
import com.sparta.bubbleclub.infra.jwt.JwtUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil,
) {

    fun signup(request: SignupRequest) {

        require(request.password == request.passwordConfirm) { throw CustomIllegalArgumentException(errorMessage = "비밀번호가 일치하지 않습니다.") }

        check(!memberRepository.existsByEmail(request.email)) { throw DuplicateArgumentException(errorMessage = "이미 사용 중인 이메일입니다.") }

        val member = Member(
            email = request.email,
            password = passwordEncoder.encode(request.password),
            nickname = request.nickname
        )

        memberRepository.save(member)
    }

    fun login(request: LoginRequest): LoginResponse? {
        val foundMember = memberRepository.findByEmail(request.email) ?: throw CustomIllegalArgumentException(errorMessage = "일치 회원 없음")
        check(passwordEncoder.matches(request.password, foundMember.password)) { throw IllegalArgumentException("일치 회원 없음") }

        return LoginResponse(jwtUtil.generateAccessToken(foundMember.id.toString(), foundMember.email))
    }
}
