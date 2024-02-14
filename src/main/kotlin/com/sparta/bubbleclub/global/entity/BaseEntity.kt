package com.sparta.bubbleclub.global.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.ZonedDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    var createdAt: ZonedDateTime = ZonedDateTime.now()

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    var updatedAt: ZonedDateTime = ZonedDateTime.now()
}
