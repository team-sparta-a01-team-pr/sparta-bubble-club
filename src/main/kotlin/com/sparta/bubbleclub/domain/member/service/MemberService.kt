package com.sparta.bubbleclub.domain.member.service

import com.sparta.bubbleclub.domain.member.dto.SignupRequest
import com.sparta.bubbleclub.domain.member.entity.Member
import com.sparta.bubbleclub.domain.member.repository.MemberRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun signup(request: SignupRequest) {

        check(memberRepository.existsByEmail(request.email)) { "email is already in use" }

        check(request.password == request.passwordConfirm) { "password does not match" }

        val member = Member(
            email = request.email,
            password = passwordEncoder.encode(request.password),
            nickname = request.nickname
        )

        memberRepository.save(member)
    }

}