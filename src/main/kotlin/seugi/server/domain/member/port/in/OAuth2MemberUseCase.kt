package seugi.server.domain.member.port.`in`

import com.fasterxml.jackson.databind.JsonNode

interface OAuth2MemberUseCase {

    fun process(code: String, registrationId: String)

    fun getAccessToken(code: String): String

    fun getUserResource(token: String): JsonNode

}