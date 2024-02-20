package com.sparta.bubbleclub.global.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.data.redis")
class RedisProperties(
    val host: String,
    val port: Int
)