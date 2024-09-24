package com.seugi.api.global.infra.oauth.google.exchange.res

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class GoogleExchangeResponse (

    @JsonProperty("id_token") val idToken: String,
    @JsonProperty("token_type") val tokenType: String,
    @JsonProperty("access_token") val accessToken: String,
    @JsonProperty("refresh_token") val refreshToken: String

)