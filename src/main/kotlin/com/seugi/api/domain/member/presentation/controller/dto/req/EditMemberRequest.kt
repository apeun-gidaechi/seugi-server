package com.seugi.api.domain.member.presentation.controller.dto.req

import com.fasterxml.jackson.annotation.JsonProperty

data class EditMemberRequest (

    @JsonProperty("picture") val picture: String?,
    @JsonProperty("name") val name: String,
    @JsonProperty("birth") val birth: String,

)