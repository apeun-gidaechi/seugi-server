package seugi.server.domain.member.port.`in`

import com.fasterxml.jackson.databind.JsonNode
import seugi.server.global.auth.jwt.JwtInfo
import seugi.server.global.auth.jwt.JwtUtils
import seugi.server.global.response.BaseResponse

interface OAuth2MemberUseCase {

    fun process(code: String, registrationId: String): BaseResponse<JwtInfo>

    fun getAccessToken(code: String): String

    fun getUserResource(token: String): JsonNode

}