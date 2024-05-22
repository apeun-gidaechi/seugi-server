package com.seugi.api.domain.member.adapter.`in`.dto.req

import com.fasterxml.jackson.annotation.JsonProperty

class OAuth2MemberRequest (

    @JsonProperty("email") val email: String,
    @JsonProperty("name") val name: String,

)