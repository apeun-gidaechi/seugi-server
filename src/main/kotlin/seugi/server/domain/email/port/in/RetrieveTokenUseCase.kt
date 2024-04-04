package seugi.server.domain.email.port.`in`

import seugi.server.global.response.BaseResponse

interface RetrieveTokenUseCase {

    fun retrieveToken(code: String): BaseResponse<String>

}