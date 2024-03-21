package seugi.server.domain.member.application.service

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import seugi.server.domain.member.adapter.`in`.dto.CreateMemberDTO
import seugi.server.domain.member.application.model.Member
import seugi.server.domain.member.application.model.value.MemberBirth
import seugi.server.domain.member.application.model.value.MemberEmail
import seugi.server.domain.member.application.model.value.MemberName
import seugi.server.domain.member.application.model.value.MemberPassword
import seugi.server.domain.member.port.`in`.CreateMemberUseCase
import seugi.server.domain.member.port.out.SaveMemberPort
import seugi.server.global.response.BaseResponse

@Service
class CreateMemberService (
    private val saveMemberPort: SaveMemberPort,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
): CreateMemberUseCase {

    override fun createMember(@RequestBody memberDTO: CreateMemberDTO)
    : BaseResponse<Any> {
        saveMemberPort.saveMember(Member(
            id = null,
            name = MemberName(memberDTO.name),
            email = MemberEmail(memberDTO.email),
            password = MemberPassword(
                bCryptPasswordEncoder.encode(memberDTO.password)
            ),
            birth = MemberBirth(memberDTO.birth)
        ))

        return BaseResponse (
            code = 200,
            success = true,
            message = "회원가입 성공 !!",
            data = emptyList()
        )
    }

}