package seugi.server.global.auth.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import seugi.server.global.exception.CustomErrorCode
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

            try  {
                jwtUtils.isExpired(token)
            } catch (e: ExpiredJwtException) {
                setErrorResponse(response, CustomErrorCode.JWT_TOKEN_EXPIRED)
            }

            val authentication: Authentication = jwtUtils.getAuthentication(token)

            SecurityContextHolder.getContext().authentication = authentication
        }

        doFilter(request, response, filterChain)
    }

    private fun setErrorResponse(
        response: HttpServletResponse,
        errorCode: CustomErrorCode
    ) {
        response.status = errorCode.code
        response.contentType = "application/json"

        response.writer.write(
            objectMapper.writeValueAsString(
                BaseResponse<String> (
                    code = errorCode.code,
                    success = false,
                    message = errorCode.msg,
                    data = emptyList()
                )
            )
        )

    }
}