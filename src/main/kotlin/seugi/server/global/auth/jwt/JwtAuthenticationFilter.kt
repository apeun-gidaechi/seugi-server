package seugi.server.global.auth.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import seugi.server.global.auth.jwt.exception.JwtErrorCode
import seugi.server.global.response.BaseResponse

class JwtAuthenticationFilter(
    private val jwtUtils: JwtUtils,
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        var token: String? = request.getHeader("Authorization")

        if (token != null && token.startsWith("Bearer ")) {
            token = jwtUtils.getToken(token)

            if (!jwtUtils.isExpired(token)) {
                val authentication: Authentication = jwtUtils.getAuthentication(token)

                SecurityContextHolder.getContext().authentication = authentication
            } else {
                setErrorResponse(response, JwtErrorCode.JWT_TOKEN_EXPIRED)
                return
            }
        }

        doFilter(request, response, filterChain)
    }

    private fun setErrorResponse(
        response: HttpServletResponse,
        errorCode: JwtErrorCode
    ) {
        response.status = errorCode.status.value()
        response.contentType = "application/json;charset=UTF-8"

        response.writer.write(
            objectMapper.writeValueAsString(
                BaseResponse<String> (
                    status = errorCode.status,
                    state = errorCode.state,
                    success = false,
                    message = errorCode.message,
                )
            )
        )

    }
}