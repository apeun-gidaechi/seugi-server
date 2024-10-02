package com.seugi.api.domain.oauth.adapter.`in`.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls
import com.seugi.api.global.common.enums.Platform

class AppleCodeRequest (

    @JsonProperty("code")
    val code: String,

    @JsonProperty("token")
    val token: String,

    @JsonProperty("platform")
    val platform: Platform,

    @JsonProperty("name")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    val name: String = "",

    @JsonProperty("email")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    val email: String = ""

) {

    fun isNameAndEmailEmpty(): Boolean {
        return name.isEmpty() && email.isEmpty()
    }

}