package com.seugi.api.domain.member.application.service

import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import com.seugi.api.domain.member.adapter.`in`.dto.LoginMemberDTO
import com.seugi.api.domain.member.adapter.out.repository.TokenRepository
import com.seugi.api.domain.member.application.model.Member
import com.seugi.api.domain.member.port.`in`.LoginMemberUseCase
import com.seugi.api.domain.member.port.out.LoadMemberPort
import com.seugi.api.global.auth.jwt.JwtInfo
import com.seugi.api.global.auth.jwt.JwtUtils
import com.seugi.api.global.auth.jwt.exception.JwtErrorCode
import com.seugi.api.global.exception.CustomException
import com.seugi.api.global.response.BaseResponse

@Service
class LoginMemberService (
    private val jwtUtils: JwtUtils,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val loadMemberPort: LoadMemberPort,
    private val tokenRepository: TokenRepository
) : LoginMemberUseCase {

    override fun loginMember(memberDTO: LoginMemberDTO): BaseResponse<JwtInfo> {
        val member: Member = loadMemberPort.loadMemberWithEmail(memberDTO.email)

        if (!bCryptPasswordEncoder.matches(memberDTO.password, member.password.value)) {
            throw CustomException(JwtErrorCode.JWT_MEMBER_NOT_MATCH)
        }

        val jwtInfo = jwtUtils.generate(member)

        tokenRepository.saveToken(
            jwtInfo.accessToken,
            jwtInfo.refreshToken
        )

        return BaseResponse (
            status = HttpStatus.OK.value(),
            success = true,
            message = "토큰 발급 성공 !!",
            data = jwtInfo
        )
    }
}