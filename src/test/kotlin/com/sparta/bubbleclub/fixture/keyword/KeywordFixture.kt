package com.sparta.bubbleclub.fixture.keyword

import com.appmattus.kotlinfixture.kotlinFixture
import com.sparta.bubbleclub.domain.keyword.entity.KeywordStore
import java.time.ZonedDateTime

object KeywordFixture {

    val fixture = kotlinFixture {
        repeatCount { 15 }
    }

    val keywordList = fixture<List<KeywordStore>> {
        generate()
    }

    fun generate(keyword: String = fixture(), count: Long = fixture(), createdAt: ZonedDateTime = fixture()) = KeywordStore(keyword, count, createdAt)

}