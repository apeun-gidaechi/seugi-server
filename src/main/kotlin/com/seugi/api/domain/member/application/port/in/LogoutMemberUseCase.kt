package com.seugi.api.domain.member.application.port.`in`

import com.seugi.api.global.response.BaseResponse

interface LogoutMemberUseCase {

    fun logoutMember(userId: Long, token: String): BaseResponse<Unit>

}