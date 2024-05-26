package com.seugi.api.domain.member.application.port.`in`

import com.seugi.api.domain.member.adapter.`in`.dto.req.RegisterMemberRequest
import com.seugi.api.global.response.BaseResponse

interface RegisterMemberUseCase {

    fun registerMember(dto: RegisterMemberRequest): BaseResponse<String>

}