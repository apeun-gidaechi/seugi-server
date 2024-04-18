package com.seugi.api.domain.member.port.`in`

import com.fasterxml.jackson.databind.JsonNode
import com.seugi.api.domain.member.adapter.`in`.dto.OAuth2MemberDTO
import com.seugi.api.global.auth.jwt.JwtInfo
import com.seugi.api.global.response.BaseResponse

interface OAuth2MemberUseCase {

    fun process(code: String, provider: String): BaseResponse<JwtInfo>

    fun complete(dto: OAuth2MemberDTO): BaseResponse<Unit>

    fun getAccessToken(code: String): String

    fun getUserResource(token: String): JsonNode

}