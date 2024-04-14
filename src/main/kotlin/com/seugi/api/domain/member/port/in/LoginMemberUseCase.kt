package com.seugi.api.domain.member.port.`in`

import com.seugi.api.domain.member.adapter.`in`.dto.LoginMemberDTO
import com.seugi.api.global.auth.jwt.JwtInfo
import com.seugi.api.global.response.BaseResponse

interface LoginMemberUseCase {

    fun loginMember(memberDTO: LoginMemberDTO): BaseResponse<JwtInfo>

}