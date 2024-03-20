package seugi.server.domain.member.port.`in`

import seugi.server.domain.member.adapter.`in`.dto.CreateMemberDTO
import seugi.server.domain.member.application.model.Member

interface CreateMemberUseCase {

    fun createMember(memberDTO: CreateMemberDTO)

}