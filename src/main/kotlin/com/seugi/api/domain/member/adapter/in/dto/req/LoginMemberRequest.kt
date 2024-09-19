package com.seugi.api.domain.member.adapter.`in`.dto.req

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginMemberRequest(

    @JsonProperty("email") val email: String,
    @JsonProperty("password") val password: String,
    @JsonProperty("code") val token: String,

    )