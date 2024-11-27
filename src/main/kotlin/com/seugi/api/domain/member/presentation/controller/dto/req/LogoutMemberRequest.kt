package com.seugi.api.domain.member.presentation.controller.dto.req

import com.fasterxml.jackson.annotation.JsonProperty

data class LogoutMemberRequest(

    @JsonProperty("fcmToken") val fcmToken: String

)