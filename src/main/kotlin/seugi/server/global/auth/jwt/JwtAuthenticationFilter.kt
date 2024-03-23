package seugi.server.global.auth.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val jwtUtils: JwtUtils
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        var token: String? = request.getHeader("Authorization")

        if (token != null && token.startsWith("Bearer ")) {
            token = jwtUtils.getToken(token)

            if (jwtUtils.isExpired(token)) {
                val authentication: Authentication = jwtUtils.getAuthentication(token)

                println(authentication)

                SecurityContextHolder.getContext().authentication = authentication
            }
        }

        doFilter(request, response, filterChain)
    }
}