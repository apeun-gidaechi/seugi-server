package com.seugi.api.domain.member.application.port.`in`

import com.seugi.api.domain.member.adapter.`in`.dto.req.EditMemberRequest
import com.seugi.api.global.response.BaseResponse

interface EditMemberUseCase {

    fun editMember(dto: EditMemberRequest, id: Long): BaseResponse<Unit>

}