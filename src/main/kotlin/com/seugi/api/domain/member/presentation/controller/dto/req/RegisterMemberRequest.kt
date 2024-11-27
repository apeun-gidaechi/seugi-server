package com.seugi.api.domain.member.presentation.controller.dto.req

import com.fasterxml.jackson.annotation.JsonProperty

data class RegisterMemberRequest (

    @JsonProperty("name") val name: String,
    @JsonProperty("email") val email: String,
    @JsonProperty("password") val password: String,
    @JsonProperty("code") val code: String

)