package com.sparta.bubbleclub.global.security.jwt

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
class JwtProperties(
    val issuer: String,
    val secret: String,
    val accessTokenExpirationHour: Long
)