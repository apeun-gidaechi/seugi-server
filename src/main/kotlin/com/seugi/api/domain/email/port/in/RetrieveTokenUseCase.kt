package com.seugi.api.domain.email.port.`in`

import com.seugi.api.global.response.BaseResponse

interface RetrieveTokenUseCase {

    fun retrieveToken(code: String): BaseResponse<String>

}