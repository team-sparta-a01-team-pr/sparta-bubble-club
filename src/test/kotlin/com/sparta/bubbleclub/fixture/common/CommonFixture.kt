package com.sparta.bubbleclub.fixture.common

import com.appmattus.kotlinfixture.decorator.nullability.NeverNullStrategy
import com.appmattus.kotlinfixture.decorator.nullability.nullabilityStrategy
import com.appmattus.kotlinfixture.decorator.optional.NeverOptionalStrategy
import com.appmattus.kotlinfixture.decorator.optional.optionalStrategy
import com.appmattus.kotlinfixture.kotlinFixture
import com.jayway.jsonpath.Configuration
import com.sparta.bubbleclub.domain.bubble.entity.Bubble
import com.sparta.bubbleclub.global.security.web.dto.MemberPrincipal
import org.springframework.data.domain.PageRequest

object CommonFixture {
    val fixture = kotlinFixture {
        nullabilityStrategy(NeverNullStrategy)
        optionalStrategy(NeverOptionalStrategy)
    }

    // page
    val firstPage = PageRequest.of(0, 10)
    val nonFirstPage = PageRequest.of(0, 10)

    // security
    val memberPrincipal = fixture<MemberPrincipal> {
        generate(id = 1, email = "test@naver.com")
    }

    private fun generate(configBuilder: Configuration.ConfigurationBuilder.() -> Unit) = fixture<Bubble> { configBuilder }
    private fun generate(id: Long = fixture(), email: String = fixture()) = MemberPrincipal(id, email)
}
