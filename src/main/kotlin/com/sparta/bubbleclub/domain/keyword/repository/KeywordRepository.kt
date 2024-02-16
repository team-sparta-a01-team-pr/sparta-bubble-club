package com.sparta.bubbleclub.domain.keyword.repository

import com.sparta.bubbleclub.domain.keyword.entity.KeywordStore
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface KeywordRepository : JpaRepository<KeywordStore, Long>, KeywordQueryDslRepository {
    fun findByKeyword(keyword: String): KeywordStore?
}
