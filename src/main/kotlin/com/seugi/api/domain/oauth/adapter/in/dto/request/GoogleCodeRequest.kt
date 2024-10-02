package com.seugi.api.domain.oauth.adapter.`in`.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.seugi.api.global.common.enums.Platform

class GoogleCodeRequest (

    @JsonProperty("code") val code: String,
    @JsonProperty("token") val token: String,
    @JsonProperty("platform") val platform: Platform

)
