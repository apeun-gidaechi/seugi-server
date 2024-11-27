package com.seugi.api.global.auth.jwt

import com.seugi.api.global.exception.CustomException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JwtAuthenticationFilter(
    private val utils: JwtUtils,
    private val service : JwtUserDetailsService
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class, CustomException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header: String? = request.getHeader("Authorization")

        if (!header.isNullOrBlank()) {
            val token = header.removePrefix("Bearer ")
            val id = utils.parse(token).id.toLong()
            val details = service.loadUserById(id)
            val authentication = UsernamePasswordAuthenticationToken(details, null, details.authorities)
            SecurityContextHolder.getContext().authentication = authentication
        }

        doFilter(request, response, filterChain)
    }

}