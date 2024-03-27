package seugi.server.domain.member.port.`in`

import seugi.server.domain.member.adapter.`in`.dto.LoginMemberDTO
import seugi.server.global.auth.jwt.JwtInfo
import seugi.server.global.response.BaseResponse

interface LoginMemberUseCase {

    fun loginMember(memberDTO: LoginMemberDTO): BaseResponse<JwtInfo>

}