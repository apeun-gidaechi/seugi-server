package com.seugi.api.domain.member.application.port.`in`

import com.fasterxml.jackson.databind.JsonNode
import com.seugi.api.domain.member.adapter.`in`.dto.req.OAuth2MemberRequest
import com.seugi.api.global.auth.jwt.JwtInfo
import com.seugi.api.global.response.BaseResponse

interface OAuth2MemberUseCase {

    fun process(code: String, provider: String): BaseResponse<JwtInfo>

    fun complete(dto: OAuth2MemberRequest): BaseResponse<Unit>

    fun getAccessToken(code: String): String

    fun getUserResource(token: String): JsonNode

}