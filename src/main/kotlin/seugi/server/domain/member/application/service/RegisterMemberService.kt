package seugi.server.domain.member.application.service

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import seugi.server.domain.member.adapter.`in`.dto.RegisterMemberDTO
import seugi.server.domain.member.application.model.Member
import seugi.server.domain.member.application.model.value.*
import seugi.server.domain.member.port.`in`.RegisterMemberUseCase
import seugi.server.domain.member.port.out.ExistMemberPort
import seugi.server.domain.member.port.out.SaveMemberPort
import seugi.server.global.exception.CustomErrorCode
import seugi.server.global.exception.CustomException
import seugi.server.global.response.BaseResponse

@Service
class RegisterMemberService (
    private val saveMemberPort: SaveMemberPort,
    private val existMemberPort: ExistMemberPort,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
): RegisterMemberUseCase {

    override fun registerMember(@RequestBody memberDTO: RegisterMemberDTO): BaseResponse<String> {
        val member = Member(
            id = null,
            name = MemberName(memberDTO.name),
            email = MemberEmail(memberDTO.email),
            password = MemberPassword(
                bCryptPasswordEncoder.encode(memberDTO.password)
            ),
            birth = MemberBirth(memberDTO.birth),
            role = MemberRole("ROLE_USER"),
            loginId = MemberLoginId(""),
            provider = MemberProvider(""),
            providerId = MemberProviderId("")
        )

        if (existMemberPort.existMemberWithEmail(member.email.value)) {
            throw CustomException(CustomErrorCode.MEMBER_ALREADY_EXIST)
        } else {
            saveMemberPort.saveMember(member)

            return BaseResponse (
                code = 200,
                success = true,
                message = "회원가입 성공 !!",
                data = emptyList()
            )
        }
    }
}