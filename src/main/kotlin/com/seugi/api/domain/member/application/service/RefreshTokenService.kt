package com.seugi.api.domain.member.application.service

import com.seugi.api.domain.member.application.port.`in`.RefreshTokenUseCase
import com.seugi.api.domain.member.application.port.out.LoadMemberPort
import com.seugi.api.global.auth.jwt.JwtUtils
import com.seugi.api.global.auth.jwt.exception.JwtErrorCode
import com.seugi.api.global.exception.CustomException
import com.seugi.api.global.response.BaseResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class RefreshTokenService (
    private val jwtUtils: JwtUtils,
    private val loadMemberPort: LoadMemberPort
) : RefreshTokenUseCase {

    override fun refreshToken(token: String): BaseResponse<String> {
        val got = jwtUtils.getToken(token)

        if (jwtUtils.isExpired(got)) {
            throw CustomException(JwtErrorCode.JWT_TOKEN_EXPIRED)
        }

        val member = loadMemberPort.loadMemberWithEmail(
            jwtUtils.getUsername(got)
        )

        return BaseResponse (
            HttpStatus.OK.value(),
            true,
            "OK",
            "리프레시 성공 !",
            jwtUtils.refreshToken(member)
        )
    }

}