package com.seugi.api.domain.member.port.`in`

import com.seugi.api.global.response.BaseResponse

interface RefreshTokenUseCase {

    fun refreshToken(token: String): BaseResponse<String>

}