package com.seugi.api.domain.member.application.service

import com.seugi.api.domain.member.application.port.`in`.RefreshTokenUseCase
import com.seugi.api.domain.member.application.port.out.LoadMemberPort
import com.seugi.api.global.auth.jwt.JwtUtils
import com.seugi.api.global.response.BaseResponse
import org.springframework.stereotype.Service

@Service
class RefreshTokenService (
    private val jwtUtils: JwtUtils,
    private val loadMemberPort: LoadMemberPort
) : RefreshTokenUseCase {

    override fun refreshToken(token: String): BaseResponse<String> {
        val got = jwtUtils.getToken(token)

        jwtUtils.checkTokenInfo(got)

        val member = loadMemberPort.loadMemberWithEmail(
            jwtUtils.getUsername(got)
        )

        return BaseResponse (
            message = "리프레시 성공 !",
            data = jwtUtils.refreshToken(member)
        )
    }

}