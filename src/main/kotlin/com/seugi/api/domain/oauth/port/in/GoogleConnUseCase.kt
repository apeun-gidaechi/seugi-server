package com.seugi.api.domain.oauth.port.`in`

import com.seugi.api.domain.oauth.adapter.`in`.dto.request.GoogleCodeRequest
import com.seugi.api.global.response.BaseResponse

interface GoogleConnUseCase {

    fun connect(id: Long, dto: GoogleCodeRequest): BaseResponse<Unit>

}