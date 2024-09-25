package com.seugi.api.global.infra.oauth.google.req

import com.fasterxml.jackson.annotation.JsonProperty
import com.seugi.api.global.auth.oauth.google.GoogleProperties

data class GoogleExchangeRequest (

    @JsonProperty("code") val code: String,
    @JsonProperty("client_id") val clientId: String,
    @JsonProperty("client_secret") val clientSecret: String,
    @JsonProperty("grant_type") val grantType: String,
    @JsonProperty("redirect-uri") val redirectUri: String

) {

    constructor(code: String, properties: GoogleProperties): this (
        code = code,
        clientId = properties.clientId,
        clientSecret = properties.clientSecret,
        grantType = properties.grantType,
        redirectUri = properties.redirectUri
    )

}