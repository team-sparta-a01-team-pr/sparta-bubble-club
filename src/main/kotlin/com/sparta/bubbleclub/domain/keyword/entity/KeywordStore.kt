package com.sparta.bubbleclub.domain.keyword.entity

import jakarta.persistence.*

@Entity
@Table(name = "keywords")
class KeywordStore(
    keyword: String,
    count: Long
) {

    @Id
    @Column(name = "keyword_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "keyword")
    val keyword: String = keyword

    @Column(name = "count")
    var count: Long = count

    fun increaseCount() = this.count++
}