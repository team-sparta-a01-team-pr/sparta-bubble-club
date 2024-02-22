package com.sparta.bubbleclub.fixture.member

import com.appmattus.kotlinfixture.kotlinFixture
import com.sparta.bubbleclub.domain.member.entity.Member

object MemberFixture {
    val fixture = kotlinFixture()

    val member = fixture<Member>()
}