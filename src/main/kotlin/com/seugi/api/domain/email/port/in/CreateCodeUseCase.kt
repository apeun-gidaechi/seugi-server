package com.seugi.api.domain.email.port.`in`

import com.seugi.api.global.response.BaseResponse

interface CreateCodeUseCase {

    fun createToken(email: String): BaseResponse<Unit>

}