package com.seugi.api.domain.member.application.service

import com.seugi.api.domain.member.application.port.`in`.RefreshTokenUseCase
import com.seugi.api.global.auth.jwt.JwtInfo
import com.seugi.api.global.auth.jwt.JwtUtils
import com.seugi.api.global.response.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class RefreshTokenService (
    private val jwtUtils: JwtUtils
) : RefreshTokenUseCase {

    override fun refreshToken(token: String): BaseResponse<JwtInfo> {
        return BaseResponse (
            HttpStatus.OK.value(),
            true,
            "OK",
            "리프레시 성공 !",
            jwtUtils.refreshToken(token)
        )
    }

}