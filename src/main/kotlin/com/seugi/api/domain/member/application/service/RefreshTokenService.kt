package com.seugi.api.domain.member.application.service

import com.seugi.api.domain.member.application.model.value.MemberRefreshToken
import com.seugi.api.domain.member.port.`in`.RefreshTokenUseCase
import com.seugi.api.domain.member.port.out.LoadMemberPort
import com.seugi.api.domain.member.port.out.SaveMemberPort
import com.seugi.api.global.auth.jwt.JwtInfo
import com.seugi.api.global.auth.jwt.JwtUtils
import com.seugi.api.global.auth.jwt.exception.JwtErrorCode
import com.seugi.api.global.exception.CustomException
import com.seugi.api.global.response.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class RefreshTokenService (
    private val jwtUtils: JwtUtils,
    private val loadMemberPort: LoadMemberPort,
    private val saveMemberPort: SaveMemberPort
) : RefreshTokenUseCase {

    override fun refreshToken(token: String): BaseResponse<JwtInfo> {
        val rToken = jwtUtils.getToken(token)

        val member = loadMemberPort.loadMemberWithEmail(
            jwtUtils.getUsername(rToken)
        )

        if (member.refreshToken.value == rToken) {
            member.refreshToken = MemberRefreshToken("")
            saveMemberPort.saveMember(member)
        } else {
            throw CustomException(JwtErrorCode.JWT_REFRESH_NOT_MATCH)
        }

        return BaseResponse (
            HttpStatus.OK.value(),
            true,
            "OK",
            "리프레시 성공 !",
            jwtUtils.refreshToken(rToken)
        )

    }

}