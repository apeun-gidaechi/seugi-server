package com.seugi.api.domain.member.adapter.`in`.dto

import com.fasterxml.jackson.annotation.JsonProperty

class OAuth2MemberDTO (

    @JsonProperty("email") val email: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("birth") val birth: String

)