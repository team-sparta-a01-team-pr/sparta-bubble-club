package com.sparta.bubbleclub.fixture.bubble

import com.appmattus.kotlinfixture.kotlinFixture
import com.sparta.bubbleclub.domain.bubble.entity.Bubble

object BubbleFixture {
    val fixture = kotlinFixture()

    val entity = fixture<Bubble>()
}