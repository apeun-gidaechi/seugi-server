package com.seugi.api.global.auth.jwt.exception

import com.seugi.api.global.exception.CommonErrorCode
import com.seugi.api.global.exception.CustomException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtExceptionFilter(
    private val responsor: ErrorResponseSender
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        try {
            filterChain.doFilter(request, response)
        } catch (e: CustomException) {
            responsor.send(response, e.customErrorCode)
        } catch (e: Exception) {
            e.printStackTrace()
            responsor.send(response, CommonErrorCode.INTERNAL_SERVER_ERROR)
        }

    }
}