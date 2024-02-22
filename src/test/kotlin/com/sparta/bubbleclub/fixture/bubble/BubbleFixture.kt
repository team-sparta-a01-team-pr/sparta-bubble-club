package com.sparta.bubbleclub.fixture.bubble

import com.appmattus.kotlinfixture.decorator.nullability.NeverNullStrategy
import com.appmattus.kotlinfixture.decorator.nullability.nullabilityStrategy
import com.appmattus.kotlinfixture.decorator.optional.NeverOptionalStrategy
import com.appmattus.kotlinfixture.decorator.optional.optionalStrategy
import com.appmattus.kotlinfixture.kotlinFixture
import com.jayway.jsonpath.Configuration.ConfigurationBuilder
import com.sparta.bubbleclub.domain.bubble.dto.request.CreateBubbleRequest
import com.sparta.bubbleclub.domain.bubble.dto.request.UpdateBubbleRequest
import com.sparta.bubbleclub.domain.bubble.dto.response.BubbleResponse
import com.sparta.bubbleclub.domain.bubble.dto.response.CustomSliceImpl
import com.sparta.bubbleclub.domain.bubble.entity.Bubble
import com.sparta.bubbleclub.domain.member.entity.Member
import java.time.ZonedDateTime

object BubbleFixture {
    val fixture = kotlinFixture {
        nullabilityStrategy(NeverNullStrategy)
        optionalStrategy(NeverOptionalStrategy)
    }

    val bubbleId = fixture<Long?>()
    val bubbleList = fixture<List<Bubble>> { generate() }

    val responseListForFirstSlice = listOf(BubbleResponse(id = 100, content = "Test", nickname = "test", createdAt = ZonedDateTime.now()))
    val responseListForNoneFirstSlice = listOf(BubbleResponse(id = 90, content = "Test", nickname = "test", createdAt = ZonedDateTime.now()))

    val bubbleFirstSlice = makeCustomSliceImpl(responseListForFirstSlice)
    val bubbleNonFirstSlice = makeCustomSliceImpl(responseListForNoneFirstSlice)
    val SliceWithoutBubbles = makeCustomSliceImpl(content = emptyList())

    val randomKeyword = fixture<String>()
    const val fixedKeyword = "Test"

    val listForSearch = fixture<List<Bubble>> {
        generate(content = fixedKeyword)
    }

    val createRequest = fixture<CreateBubbleRequest>()
    val updateRequest = fixture<UpdateBubbleRequest>()

    val bubble = fixture<Bubble>()


    val firstBubbleId = 100

    private fun generate(configBuilder: ConfigurationBuilder.() -> Unit) = fixture<Bubble> { configBuilder }
    private fun generate(content: String = fixture(), member: Member = fixture()) = Bubble(content, member)
    private fun makeCustomSliceImpl(content: List<BubbleResponse>?) = CustomSliceImpl(content, 10, true)
}