package seugi.server.domain.member.port.`in`

import seugi.server.domain.member.adapter.`in`.dto.CreateMemberDTO
import seugi.server.global.response.BaseResponse

interface CreateMemberUseCase {

    fun createMember(memberDTO: CreateMemberDTO): BaseResponse<Any>

}