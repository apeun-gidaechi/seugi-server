package com.seugi.api.global.infra.oauth.google.parse

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class GoogleParseResponse (

    @JsonProperty("name") val name: String,
    @JsonProperty("email") val email: String,

)