package com.sparta.bubbleclub.global.cache

import com.sparta.bubbleclub.infra.redis.CacheKey
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableCaching
@Configuration
class CachingConfig {

    @Bean
    fun cacheManager(): CacheManager = ConcurrentMapCacheManager(CacheKey.GET_ALL_BUBBLES, CacheKey.GET_BUBBLES_BY_KEYWORD)
}
