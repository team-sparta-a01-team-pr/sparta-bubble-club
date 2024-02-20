package com.sparta.bubbleclub.domain.bubble.dto.response

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.SliceImpl
import org.springframework.data.domain.Pageable

class CustomSliceImpl<T> @JsonCreator(mode = JsonCreator.Mode.PROPERTIES) constructor(
    @JsonProperty("content") content: List<T>?,
    @JsonProperty("size") size: Int,
    @JsonProperty("hasNext") hasNext: Boolean
) : SliceImpl<T>(content!!, PageRequest.of(0, size), hasNext) {
    @JsonIgnore
    override fun getPageable(): Pageable {
        return super.getPageable()
    }
}