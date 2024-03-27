package seugi.server.domain.member.adapter.`in`.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginMemberDTO (

    @JsonProperty("email") val email: String,
    @JsonProperty("password") val password: String

)