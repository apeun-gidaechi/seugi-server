package com.seugi.api.global.infra.oauth.google.exchange

import com.fasterxml.jackson.annotation.JsonProperty
import com.seugi.api.global.auth.oauth.GoogleProperties

data class GoogleExchangeRequest (

    @JsonProperty("code") val code: String,
    @JsonProperty("client_id") var clientId: String,
    @JsonProperty("client_secret") var clientSecret: String,
    @JsonProperty("redirect_uri") var redirectUri: String,
    @JsonProperty("grant_type") var grantType: String

) {

    constructor(code: String, properties: GoogleProperties): this (
        code = code,
        clientId = properties.clientId,
        clientSecret = properties.clientSecret,
        redirectUri = properties.redirectUri,
        grantType = "authorization_code"
    )

}