package com.seugi.api.domain.member.application.service

import com.seugi.api.domain.member.adapter.out.repository.MemberTokenRepository
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
    private val memberTokenRepository: MemberTokenRepository,
    private val loadMemberPort: LoadMemberPort
) : RefreshTokenUseCase {

    override fun refreshToken(token: String): BaseResponse<String> {
        val refreshToken: String = memberTokenRepository.findByIdOrNull(token)?.refreshToken
            ?: throw CustomException(JwtErrorCode.JWT_TOKEN_NOT_VALID)

        val member = loadMemberPort.loadMemberWithEmail(
            jwtUtils.getUsername(
                jwtUtils.getToken(refreshToken)
            )
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