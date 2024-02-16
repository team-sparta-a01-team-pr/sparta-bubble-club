package com.sparta.bubbleclub.domain.keyword.service

import com.sparta.bubbleclub.domain.keyword.dto.response.KeywordResponse
import com.sparta.bubbleclub.domain.keyword.repository.KeywordRepository
import com.sparta.bubbleclub.global.exception.common.NoSuchEntityException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class KeywordService(
    private val keywordRepository: KeywordRepository
) {

    @Transactional
    fun increaseKeywordCount(keyword: String) {
        val keyword = keywordRepository.findByKeyword(keyword) ?: throw NoSuchEntityException(errorMessage = "해당하는 Keyword는 없습니다")

        keyword.increaseCount()
    }

    fun getPopularKeywords(): List<KeywordResponse> {
        return keywordRepository.getPopularKeywords()
    }
}