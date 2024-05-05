package com.seugi.api.domain.member.adapter.`in`.controller

import com.seugi.api.domain.member.application.port.`in`.RefreshTokenUseCase
import com.seugi.api.global.auth.jwt.JwtInfo
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/member")
class RefreshTokenController (
    private val refreshTokenUseCase: RefreshTokenUseCase
) {

    @GetMapping("/refresh")
    fun refreshToken(token: String): BaseResponse<JwtInfo> {
        return refreshTokenUseCase.refreshToken(token)
    }

}