package com.seugi.api.domain.profile.adapter.`in`.request

data class EditProfileRequest (

    val status: String?, // 상태메시지
    val nick: String?, // 닉네임
    val spot: String?, // 직위
    val belong: String?, // 소속
    val phone: String?, // 휴대전화번호
    val wire: String?, // 유선전화번호
    val location: String?, // 근무위치

)