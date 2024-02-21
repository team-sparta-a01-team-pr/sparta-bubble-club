package com.sparta.bubbleclub.domain.keyword.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.ZonedDateTime

@Entity
@Table(name = "keywords")
class KeywordStore(
    keyword: String,
    count: Long,
    createdAt: ZonedDateTime
) {

    @Id
    @Column(name = "keyword_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "keyword")
    val keyword: String = keyword

    @Column(name = "count")
    var count: Long = count

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP(6)")
    @CreationTimestamp
    var createdAt: ZonedDateTime = createdAt

    fun increaseCount() = this.count++
}