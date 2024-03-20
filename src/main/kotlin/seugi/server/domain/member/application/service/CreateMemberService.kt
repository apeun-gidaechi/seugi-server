package seugi.server.domain.member.application.service

import org.springframework.stereotype.Service
import seugi.server.domain.member.adapter.`in`.dto.CreateMemberDTO
import seugi.server.domain.member.application.model.Member
import seugi.server.domain.member.port.`in`.CreateMemberUseCase
import seugi.server.domain.member.port.out.SaveMemberPort

@Service
class CreateMemberService (
    private val saveMemberPort: SaveMemberPort
): CreateMemberUseCase {

    override fun createMember(memberDTO: CreateMemberDTO) {
        saveMemberPort.saveMember(Member(
            null
        ))
    }

}