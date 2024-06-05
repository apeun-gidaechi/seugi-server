package com.seugi.api.domain.member.application.port.`in`

import com.seugi.api.global.response.BaseResponse

interface RemoveMemberUseCase {

    fun removeMember(id: Long) : BaseResponse<Unit>

}