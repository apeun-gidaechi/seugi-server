package com.seugi.api.domain.member.application.service

import com.seugi.api.domain.member.adapter.out.entity.MemberTokenEntity
import com.seugi.api.domain.member.adapter.out.repository.MemberTokenRepository
import com.seugi.api.domain.member.port.`in`.RefreshTokenUseCase
import com.seugi.api.domain.member.port.out.LoadMemberPort
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
    private val loadMemberPort: LoadMemberPort,
    private val memberTokenRepository: MemberTokenRepository
) : RefreshTokenUseCase {

    override fun saveToken(accessToken: String, refreshToken: String) {
        memberTokenRepository.save(
            MemberTokenEntity(accessToken, refreshToken)
        )
    }

    override fun loadToken(accessToken: String): String {
        val tokenEntity: MemberTokenEntity = memberTokenRepository.findByIdOrNull(accessToken)
            ?: throw CustomException(JwtErrorCode.JWT_TOKEN_NOT_VALID)

        return tokenEntity.refreshToken
    }

    override fun refreshToken(token: String): BaseResponse<String> {
        val member = loadMemberPort.loadMemberWithEmail(
            jwtUtils.getUsername(
                jwtUtils.getToken(token)
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