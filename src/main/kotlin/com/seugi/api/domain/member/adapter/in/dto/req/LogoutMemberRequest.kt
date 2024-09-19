package com.seugi.api.domain.member.adapter.`in`.dto.req

import com.fasterxml.jackson.annotation.JsonProperty

data class LogoutMemberRequest(
    @JsonProperty("fcmToken") val fcmToken: String,
)