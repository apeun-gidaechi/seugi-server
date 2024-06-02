package com.seugi.api.domain.member.application.service

import com.seugi.api.domain.email.application.service.ConfirmCodeService
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import com.seugi.api.domain.member.adapter.`in`.dto.req.RegisterMemberRequest
import com.seugi.api.domain.member.application.model.Member
import com.seugi.api.domain.member.application.model.value.*
import com.seugi.api.domain.member.application.exception.MemberErrorCode
import com.seugi.api.domain.member.application.port.`in`.RegisterMemberUseCase
import com.seugi.api.domain.member.application.port.out.ExistMemberPort
import com.seugi.api.domain.member.application.port.out.SaveMemberPort
import com.seugi.api.global.exception.CustomException
import com.seugi.api.global.response.BaseResponse

@Service
class RegisterMemberService(
    private val saveMemberPort: SaveMemberPort,
    private val existMemberPort: ExistMemberPort,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val confirmCodeService: ConfirmCodeService
) : RegisterMemberUseCase {

    override fun registerMember(@RequestBody dto: RegisterMemberRequest): BaseResponse<String> {
        val member = Member(
            id = null,
            name = MemberName(dto.name),
            email = MemberEmail(dto.email),
            picture = MemberPicture(""),
            password = MemberPassword(
                bCryptPasswordEncoder.encode(dto.password)
            ),
            birth = MemberBirth(""),
            role = MemberRole("ROLE_USER"),
            loginId = MemberLoginId(""),
            provider = MemberProvider(""),
            providerId = MemberProviderId(""),
        )

        confirmCodeService.confirmCode(dto.email, dto.code)

        if (existMemberPort.existMemberWithEmail(member.email.value)) {
            throw CustomException(MemberErrorCode.MEMBER_ALREADY_EXIST)
        }

        saveMemberPort.saveMember(member)

        return BaseResponse(
            status = HttpStatus.OK.value(),
            success = true,
            message = "회원가입 성공 !!",
        )
    }
}