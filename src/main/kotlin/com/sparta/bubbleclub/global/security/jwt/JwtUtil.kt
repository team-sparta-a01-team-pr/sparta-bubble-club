package com.sparta.bubbleclub.global.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.util.*

@Component
class JwtUtil {
    // TODO: 추후 @Value("\${..}") 등 이용하여 생성자에서 환경 변수로 받게할 것
    private val issuer: String = "temporary"
    private val secret: String = "ABCDEabcde1234567890ABCDEabcde12"
    private val accessTokenExpirationHour: Long = 1
    private val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))

    fun generateAccessToken(subject: String, email: String): String {
        return generateToken(subject, email, Duration.ofHours(accessTokenExpirationHour))
    }

    private fun generateToken(subject: String, email: String, expirationPeriod: Duration): String {
        val claims: Claims = Jwts.claims()
            .add(mapOf("email" to email))
            .build()
        val now = Instant.now()

        return Jwts.builder()
            .subject(subject)
            .issuer(issuer)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(expirationPeriod)))
            .claims(claims)
            .signWith(key)
            .compact()
    }

    fun validateToken(jwt: String): Result<Jws<Claims>> {
        return runCatching {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt)
        }
    }
}
