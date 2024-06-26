package com.seugi.api.domain.member.application.service

import com.seugi.api.domain.email.application.service.ConfirmCodeService
import com.seugi.api.domain.member.adapter.`in`.dto.req.RegisterMemberRequest
import com.seugi.api.domain.member.application.exception.MemberErrorCode
import com.seugi.api.domain.member.application.model.Member
import com.seugi.api.domain.member.application.port.`in`.RegisterMemberUseCase
import com.seugi.api.domain.member.application.port.out.ExistMemberPort
import com.seugi.api.domain.member.application.port.out.SaveMemberPort
import com.seugi.api.global.auth.jwt.JwtInfo
import com.seugi.api.global.auth.jwt.JwtUtils
import com.seugi.api.global.exception.CustomException
import com.seugi.api.global.response.BaseResponse
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

@Service
class RegisterMemberService(
    private val saveMemberPort: SaveMemberPort,
    private val existMemberPort: ExistMemberPort,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val confirmCodeService: ConfirmCodeService,
    private val jwtUtils: JwtUtils
) : RegisterMemberUseCase {

    override fun registerMember(@RequestBody dto: RegisterMemberRequest): BaseResponse<JwtInfo> {
        val member = Member(dto, bCryptPasswordEncoder.encode(dto.password))

        confirmCodeService.confirmCode(dto.email, dto.code)

        if (existMemberPort.existMemberWithEmail(member.email.value)) {
            throw CustomException(MemberErrorCode.MEMBER_ALREADY_EXIST)
        }

        saveMemberPort.saveMember(member)

        return BaseResponse(
            message = "회원가입 성공 !!",
            data = jwtUtils.generate(member)
        )
    }
}