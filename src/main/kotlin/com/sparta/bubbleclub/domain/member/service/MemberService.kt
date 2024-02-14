package com.sparta.bubbleclub.domain.member.service

import com.sparta.bubbleclub.domain.member.dto.SignupRequest
import com.sparta.bubbleclub.domain.member.entity.Member
import com.sparta.bubbleclub.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {

    fun signup(request: SignupRequest) {

        check(request.password == request.passwordConfirm) { "password does not match" }

        // TODO: 비밀번호 암호화
        val encodedPassword = "암호화~~"

        val member = Member(
            email = request.email,
            password = encodedPassword,
            nickname = request.nickname
        )

        memberRepository.save(member)
    }

}