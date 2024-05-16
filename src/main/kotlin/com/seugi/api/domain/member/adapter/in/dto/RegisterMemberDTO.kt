package com.seugi.api.domain.member.adapter.`in`.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class RegisterMemberDTO (

    @JsonProperty("name") val name: String,
    @JsonProperty("email") val email: String,
    @JsonProperty("password") val password: String,
    @JsonProperty("token") val token: String

)