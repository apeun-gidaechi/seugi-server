package com.seugi.api.global.infra.oauth.apple.req

import com.seugi.api.global.auth.oauth.apple.AppleProperties

data class AppleExchangeRequest (
    val clientId: String,
    val clientSecret: String,
    val code: String,
    val grantType: String,
    val redirectUri: String,
) {
    constructor(code: String, clientId: String, clientSecret: String, properties: AppleProperties) : this (
        code = code,
        clientId = clientId,
        clientSecret = clientSecret,
        grantType = properties.grantType,
        redirectUri = properties.redirectUri
    )

    fun toQueryMap(): Map<String, String> {
        val queryMap: MutableMap<String, String> = HashMap()

        queryMap["client_id"] = clientId
        queryMap["client_secret"] = clientSecret
        queryMap["code"] = code
        queryMap["grant_type"] = grantType
        queryMap["redirect_uri"] = redirectUri

        return queryMap
    }
}