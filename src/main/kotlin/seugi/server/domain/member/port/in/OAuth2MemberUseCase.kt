package seugi.server.domain.member.port.`in`

import com.fasterxml.jackson.databind.JsonNode
import seugi.server.domain.member.adapter.`in`.dto.OAuth2MemberDTO
import seugi.server.global.auth.jwt.JwtInfo
import seugi.server.global.response.BaseResponse

interface OAuth2MemberUseCase {

    fun process(code: String, provider: String): BaseResponse<JwtInfo>

    fun complete(dto: OAuth2MemberDTO): BaseResponse<Unit>

    fun getAccessToken(code: String): String

    fun getUserResource(token: String): JsonNode

}