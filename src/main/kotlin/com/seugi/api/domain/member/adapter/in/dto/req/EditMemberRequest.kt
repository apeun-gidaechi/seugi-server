package com.seugi.api.domain.member.adapter.`in`.dto.req

import com.fasterxml.jackson.annotation.JsonProperty

class EditMemberRequest (

    @JsonProperty("picture") val picture: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("birth") val birth: String,

)