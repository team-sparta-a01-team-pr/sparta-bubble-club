package com.sparta.bubbleclub.domain.keyword.service

import com.sparta.bubbleclub.domain.bubble.dto.request.SearchKeywordRequest
import com.sparta.bubbleclub.domain.keyword.dto.response.KeywordResponse
import com.sparta.bubbleclub.domain.keyword.repository.KeywordRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class KeywordService(
    private val keywordRepository: KeywordRepository
) {

    @Transactional
    fun increaseKeywordCount(request: SearchKeywordRequest) {
        val keyword = keywordRepository.findByKeyword(request.keyword) ?: keywordRepository.save(request.toEntity())

        keyword.increaseCount()
    }

    fun getPopularKeywords(): List<KeywordResponse> {
        return keywordRepository.getPopularKeywords()
    }
}