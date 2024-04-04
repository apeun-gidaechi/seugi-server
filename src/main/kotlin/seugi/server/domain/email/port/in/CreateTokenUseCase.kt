package seugi.server.domain.email.port.`in`

import seugi.server.global.response.BaseResponse

interface CreateTokenUseCase {

    fun createToken(email: String): BaseResponse<Unit>

}