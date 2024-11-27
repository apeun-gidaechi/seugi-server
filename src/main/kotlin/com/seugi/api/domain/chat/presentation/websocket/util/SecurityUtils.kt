package com.seugi.api.domain.chat.presentation.websocket.util

import com.seugi.api.global.auth.jwt.JwtUserDetails
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import java.security.Principal

object SecurityUtils {

    fun getUserId(principal: Principal?): Long {
        return (principal as? UsernamePasswordAuthenticationToken)?.principal.let { it as? JwtUserDetails }?.member?.id
            ?: -1
    }
}