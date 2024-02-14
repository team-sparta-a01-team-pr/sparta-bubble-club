package com.sparta.bubbleclub.domain.member.service

import com.sparta.bubbleclub.domain.member.dto.LoginRequest
import com.sparta.bubbleclub.domain.member.dto.LoginResponse
import com.sparta.bubbleclub.domain.member.dto.SignupRequest
import com.sparta.bubbleclub.domain.member.entity.Member
import com.sparta.bubbleclub.domain.member.repository.MemberRepository
import com.sparta.bubbleclub.global.security.jwt.JwtUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil,
) {

    fun signup(request: SignupRequest) {

        require(request.password == request.passwordConfirm) { "password does not match" }

        check(!memberRepository.existsByEmail(request.email)) { "email is already in use" }

        val member = Member(
            email = request.email,
            password = passwordEncoder.encode(request.password),
            nickname = request.nickname
        )

        memberRepository.save(member)
    }

    fun login(request: LoginRequest): LoginResponse? {
        val foundMember = memberRepository.findByEmail(request.email) ?: throw IllegalArgumentException("일치 회원 없음") // TODO 예외 처리 종합 필요
        check(passwordEncoder.matches(request.password, foundMember.password)) { throw IllegalArgumentException("일치 회원 없음") }

        return LoginResponse(jwtUtil.generateAccessToken(foundMember.id.toString(), foundMember.email))
    }
}
