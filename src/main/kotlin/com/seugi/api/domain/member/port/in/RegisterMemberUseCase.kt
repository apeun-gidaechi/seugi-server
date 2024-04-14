package com.seugi.api.domain.member.port.`in`

import com.seugi.api.domain.member.adapter.`in`.dto.RegisterMemberDTO
import com.seugi.api.global.response.BaseResponse

interface RegisterMemberUseCase {

    fun registerMember(memberDTO: RegisterMemberDTO): BaseResponse<String>

}