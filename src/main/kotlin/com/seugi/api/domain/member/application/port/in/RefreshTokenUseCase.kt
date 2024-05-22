package com.seugi.api.domain.member.application.port.`in`

import com.seugi.api.global.response.BaseResponse

interface RefreshTokenUseCase {

    fun refreshToken(token: String): BaseResponse<String>

}