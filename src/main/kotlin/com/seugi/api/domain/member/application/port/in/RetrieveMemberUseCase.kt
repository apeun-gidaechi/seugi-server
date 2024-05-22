package com.seugi.api.domain.member.application.port.`in`

import com.seugi.api.domain.member.adapter.`in`.dto.res.RetrieveMemberResponse
import com.seugi.api.global.response.BaseResponse

interface RetrieveMemberUseCase {

    fun retrieveMember(memberId: Long): BaseResponse<RetrieveMemberResponse>

}