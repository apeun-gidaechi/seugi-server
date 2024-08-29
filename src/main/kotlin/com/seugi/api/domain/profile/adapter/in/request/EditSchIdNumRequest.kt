package com.seugi.api.domain.profile.adapter.`in`.request

import com.fasterxml.jackson.annotation.JsonProperty

data class EditSchIdNumRequest (

    @JsonProperty("id") val id: Long, // 바꿀 유저의 ID
    @JsonProperty("schGrade") val schGrade: Int = 0, // 학년
    @JsonProperty("schClass") val schClass: Int = 0, // 반
    @JsonProperty("schNumber") val schNumber: Int = 0, // 번호

)