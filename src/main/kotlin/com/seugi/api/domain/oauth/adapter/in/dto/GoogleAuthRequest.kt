package com.seugi.api.domain.oauth.adapter.`in`.dto

import com.fasterxml.jackson.annotation.JsonProperty

class GoogleAuthRequest (

    @JsonProperty("code") val code: String

)