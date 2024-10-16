package com.seugi.api.domain.profile.adapter.`in`.request

import com.fasterxml.jackson.annotation.JsonProperty

class EditProfileRequest (

    @JsonProperty("status") val status: String?, // 상태메시지
    @JsonProperty("nick") val nick: String?, // 닉네임
    @JsonProperty("spot") val spot: String?, // 직위
    @JsonProperty("belong") val belong: String?, // 소속
    @JsonProperty("phone") val phone: String?, // 휴대전화번호
    @JsonProperty("wire") val wire: String?, // 유선전화번호
    @JsonProperty("location") val location: String?, // 근무위치

)