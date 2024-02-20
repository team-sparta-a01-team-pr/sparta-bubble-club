package com.sparta.bubbleclub.global.property

import com.sparta.bubbleclub.infra.jwt.JwtProperties
import com.sparta.bubbleclub.infra.redis.RedisProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(JwtProperties::class, RedisProperties::class)
class PropertyConfig