package com.seugi.api.global.auth.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import com.seugi.api.global.auth.jwt.exception.JwtErrorCode
import com.seugi.api.global.auth.jwt.exception.type.JwtErrorType
import com.seugi.api.global.response.BaseResponse
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

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

            when (jwtUtils.isExpired(token)) {
                JwtErrorType.OK -> {
                    try {
                        val authentication: Authentication = jwtUtils.getAuthentication(token)

                        SecurityContextHolder.getContext().authentication = authentication
                    } catch (e: Exception) {
                        setErrorResponse(response, JwtErrorCode.JWT_NULL_EXCEPTION)
                        return
                    }
                }

                JwtErrorType.ExpiredJwtException -> setErrorResponse(response, JwtErrorCode.JWT_TOKEN_EXPIRED)
                JwtErrorType.SignatureException -> setErrorResponse(response, JwtErrorCode.JWT_TOKEN_SIGNATURE_ERROR)
                JwtErrorType.MalformedJwtException -> setErrorResponse(response, JwtErrorCode.JWT_TOKEN_ERROR)
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
                BaseResponse<String>(
                    status = errorCode.status.value(),
                    state = errorCode.state,
                    success = false,
                    message = errorCode.message,
                )
            )
        )

    }
}