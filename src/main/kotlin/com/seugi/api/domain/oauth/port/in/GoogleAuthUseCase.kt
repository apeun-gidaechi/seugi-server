package com.seugi.api.domain.oauth.port.`in`

import com.seugi.api.domain.oauth.adapter.`in`.dto.GoogleAuthRequest
import com.seugi.api.global.auth.jwt.JwtInfo
import com.seugi.api.global.response.BaseResponse

interface GoogleAuthUseCase {

    fun authenticate(dto: GoogleAuthRequest): BaseResponse<JwtInfo>

}