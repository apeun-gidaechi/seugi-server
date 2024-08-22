package com.seugi.api.global.auth.jwt

import com.seugi.api.global.exception.CustomException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JwtAuthenticationFilter(
    private val jwtUtils: JwtUtils,
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class, CustomException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token: String? = request.getHeader("Authorization")

        if (!token.isNullOrBlank()) {
            jwtUtils.checkTokenInfo(jwtUtils.getToken(token))
            SecurityContextHolder.getContext().authentication = jwtUtils.getAuthentication(token)
        }

        doFilter(request, response, filterChain)
    }

}