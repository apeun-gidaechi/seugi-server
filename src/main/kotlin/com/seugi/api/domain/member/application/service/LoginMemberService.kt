package com.seugi.api.domain.member.application.service

import com.seugi.api.domain.member.adapter.`in`.dto.req.LoginMemberRequest
import com.seugi.api.domain.member.application.model.Member
import com.seugi.api.domain.member.application.port.`in`.LoginMemberUseCase
import com.seugi.api.domain.member.application.port.out.LoadMemberPort
import com.seugi.api.domain.member.application.port.out.SaveMemberPort
import com.seugi.api.global.auth.jwt.JwtInfo
import com.seugi.api.global.auth.jwt.JwtUtils
import com.seugi.api.global.auth.jwt.exception.JwtErrorCode
import com.seugi.api.global.exception.CustomException
import com.seugi.api.global.response.BaseResponse
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LoginMemberService(
    private val jwtUtils: JwtUtils,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val loadMemberPort: LoadMemberPort,
    private val saveMemberPort: SaveMemberPort,
) : LoginMemberUseCase {

    @Transactional
    override fun loginMember(memberDTO: LoginMemberRequest): BaseResponse<JwtInfo> {
        val member: Member = loadMemberPort.loadMemberWithEmail(memberDTO.email)

        if (!bCryptPasswordEncoder.matches(memberDTO.password, member.password.value)) {
            throw CustomException(JwtErrorCode.JWT_MEMBER_NOT_MATCH)
        }

        member.addFCMToken(memberDTO.token)
        saveMemberPort.saveMember(member)

        val jwtInfo = jwtUtils.generate(member)

        return BaseResponse (
            message = "토큰 발급 성공 !!",
            data = jwtInfo
        )
    }
}