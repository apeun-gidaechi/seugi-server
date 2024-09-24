package com.seugi.api.domain.oauth.adapter.`in`.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

class GoogleCodeRequest (

    @JsonProperty("code") val code: String,

)
