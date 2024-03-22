package seugi.server.domain.member.port.`in`

import seugi.server.domain.member.adapter.`in`.dto.RegisterMemberDTO
import seugi.server.global.response.BaseResponse

interface RegisterMemberUseCase {

    fun registerMember(memberDTO: RegisterMemberDTO): BaseResponse<Any>

}