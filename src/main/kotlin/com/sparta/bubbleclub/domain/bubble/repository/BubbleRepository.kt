package com.sparta.bubbleclub.domain.bubble.repository

import com.sparta.bubbleclub.domain.bubble.entity.Bubble
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BubbleRepository: JpaRepository<Bubble, Long>, BubbleQueryDslRepository {
}