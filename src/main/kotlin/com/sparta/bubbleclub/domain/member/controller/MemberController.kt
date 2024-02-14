package com.sparta.bubbleclub.domain.member.controller

import com.sparta.bubbleclub.domain.member.dto.SignupRequest
import com.sparta.bubbleclub.domain.member.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/v1/members")
class MemberController(
    private val memberService: MemberService
) {

    @PostMapping("/signup")
    fun signup(@RequestBody request: SignupRequest): ResponseEntity<Unit> {
        memberService.signup(request)
        return ResponseEntity.created(URI.create(String.format("/api/v1/members/login"))).build()
    }

}