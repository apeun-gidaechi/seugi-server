package com.seugi.api.domain.oauth.port.`in`

import com.seugi.api.domain.oauth.adapter.`in`.dto.request.AppleCodeRequest
import com.seugi.api.global.auth.jwt.JwtInfo
import com.seugi.api.global.response.BaseResponse

interface AppleAuthUseCase {

    fun authenticate(dto: AppleCodeRequest): BaseResponse<JwtInfo>

}