package com.seugi.api.domain.oauth.application.model

import com.seugi.api.domain.member.application.model.Member
import com.seugi.api.domain.oauth.application.model.vo.*

class OAuth (

    val id: OAuthId,
    val member: Member,
    val provider: OAuthProvider,
    val sub: OAuthSub,
    val accessToken: OAuthAccessToken,
    val refreshToken: OAuthRefreshToken

) {

    constructor(provider: String, sub: String, accessToken: String, refreshToken: String, member: Member): this (
        id = OAuthId(0),
        member = member,
        provider = OAuthProvider(provider),
        sub = OAuthSub(sub),
        accessToken = OAuthAccessToken(accessToken),
        refreshToken = OAuthRefreshToken(refreshToken)
    )

}

