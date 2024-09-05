package com.seugi.api.domain.oauth.port.`in`

import com.seugi.api.domain.oauth.adapter.`in`.dto.GoogleCodeRequest
import com.seugi.api.global.auth.jwt.JwtInfo
import com.seugi.api.global.response.BaseResponse

interface GoogleAuthUseCase {

    fun authenticate(dto: GoogleCodeRequest): BaseResponse<JwtInfo>

}