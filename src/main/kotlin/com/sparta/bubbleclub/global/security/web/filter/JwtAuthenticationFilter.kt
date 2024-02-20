package com.sparta.bubbleclub.global.security.web.filter

import com.sparta.bubbleclub.infra.jwt.JwtAuthenticationToken
import com.sparta.bubbleclub.infra.jwt.JwtUtil
import com.sparta.bubbleclub.global.security.web.dto.MemberPrincipal
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtUtil: JwtUtil
) : OncePerRequestFilter() {

    companion object {
        private val BEARER_PATTERN = Regex("^Bearer (.+?)$")
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwt = request.getBearerToken()

        if (jwt != null) {
            jwtUtil.validateToken(jwt)
                .onSuccess {
                    val userId = it.payload.subject.toLong()
                    val email = it.payload.get("email", String::class.java)

                    JwtAuthenticationToken(
                        MemberPrincipal(id = userId, email = email),
                        WebAuthenticationDetailsSource().buildDetails(request),
                    )
                        .also { authentication -> SecurityContextHolder.getContext().authentication = authentication }
                }
        }
        filterChain.doFilter(request, response)
    }

    private fun HttpServletRequest.getBearerToken(): String? {
        return this.getHeader(HttpHeaders.AUTHORIZATION)
            ?.let { BEARER_PATTERN.find(it)?.groupValues?.get(1) }
    }
}
