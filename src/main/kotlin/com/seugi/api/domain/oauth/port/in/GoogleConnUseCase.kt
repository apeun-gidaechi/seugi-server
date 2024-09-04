package com.seugi.api.domain.oauth.port.`in`

import com.seugi.api.domain.oauth.adapter.`in`.dto.GoogleConnRequest
import com.seugi.api.global.response.BaseResponse

interface GoogleConnUseCase {

    fun connect(id: Long, code: GoogleConnRequest): BaseResponse<Unit>

}