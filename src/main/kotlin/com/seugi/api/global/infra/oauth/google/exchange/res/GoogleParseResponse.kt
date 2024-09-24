package com.seugi.api.global.infra.oauth.google.exchange.res

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class GoogleParseResponse (

    @JsonProperty("name") val name: String,
    @JsonProperty("email") val email: String,
    @JsonProperty("sub") val sub: String

)