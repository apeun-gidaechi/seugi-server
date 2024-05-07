package com.seugi.api.domain.member.port.`in`

import com.seugi.api.global.response.BaseResponse

interface RefreshTokenUseCase {

    fun saveToken(accessToken: String, refreshToken: String)

    fun loadToken(accessToken: String): String

    fun refreshToken(token: String): BaseResponse<String>

}