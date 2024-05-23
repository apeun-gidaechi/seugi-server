package com.seugi.api.domain.member.application.port.`in`

import com.seugi.api.domain.member.adapter.`in`.dto.req.LoginMemberRequest
import com.seugi.api.global.auth.jwt.JwtInfo
import com.seugi.api.global.response.BaseResponse

interface LoginMemberUseCase {

    fun loginMember(memberDTO: LoginMemberRequest): BaseResponse<JwtInfo>

}