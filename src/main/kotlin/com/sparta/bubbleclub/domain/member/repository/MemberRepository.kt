package com.sparta.bubbleclub.domain.member.repository

import com.sparta.bubbleclub.domain.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long>