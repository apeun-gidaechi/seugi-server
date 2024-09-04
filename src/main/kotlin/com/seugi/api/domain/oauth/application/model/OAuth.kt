package com.seugi.api.domain.oauth.application.model

import com.seugi.api.domain.member.application.model.Member
import com.seugi.api.domain.oauth.application.model.vo.*
import com.seugi.api.global.infra.oauth.google.exchange.GoogleExchangeResponse

class OAuth (

    val id: OAuthId,
    val memberId: Member,
    val provider: OAuthProvider,
    val accessToken: OAuthAccessToken,
    val refreshToken: OAuthRefreshToken

) {

    constructor(dto: GoogleExchangeResponse, member: Member): this (
        id = OAuthId(0),
        memberId = member,
        provider = OAuthProvider("google"),
        accessToken = OAuthAccessToken(dto.accessToken),
        refreshToken = OAuthRefreshToken(dto.refreshToken)
    )

}

