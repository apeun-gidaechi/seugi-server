package com.seugi.api.global.auth.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import com.seugi.api.global.auth.jwt.exception.JwtErrorCode
import com.seugi.api.global.auth.jwt.exception.type.JwtErrorType
import com.seugi.api.global.response.BaseResponse
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
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
        val token: String? = request.getHeader("Authorization")
        val path: String = request.servletPath

        if (path.startsWith("/member/") ||
            path.startsWith("/oauth2/") ||
            path.startsWith("/email/") ||
            path.startsWith("/stomp/")
        ) {
            filterChain.doFilter(request, response)
            return
        }

        if (token.isNullOrEmpty() || !token.startsWith("Bearer ")) {
            setErrorResponse(response, JwtErrorCode.JWT_EMPTY_EXCEPTION)
        } else {
            when (jwtUtils.checkTokenInfo(jwtUtils.getToken(token))) {
                JwtErrorType.OK -> {
                    SecurityContextHolder.getContext().authentication = jwtUtils.getAuthentication(token)
                    doFilter(request, response, filterChain)
                }

                JwtErrorType.ExpiredJwtException -> setErrorResponse(response, JwtErrorCode.JWT_TOKEN_EXPIRED)
                JwtErrorType.SignatureException -> setErrorResponse(response, JwtErrorCode.JWT_TOKEN_SIGNATURE_ERROR)
                JwtErrorType.MalformedJwtException -> setErrorResponse(response, JwtErrorCode.JWT_TOKEN_ERROR)
                JwtErrorType.UnsupportedJwtException -> setErrorResponse(
                    response,
                    JwtErrorCode.JWT_TOKEN_UNSUPPORTED_ERROR
                )

                JwtErrorType.IllegalArgumentException -> setErrorResponse(
                    response,
                    JwtErrorCode.JWT_TOKEN_ILL_EXCEPTION
                )

                JwtErrorType.UNKNOWN_EXCEPTION -> setErrorResponse(response, JwtErrorCode.JWT_UNKNOWN_EXCEPTION)
            }
        }
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