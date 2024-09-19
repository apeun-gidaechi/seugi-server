package com.seugi.api.global.infra.fcm

enum class FCMEnums(
    val type: String,
) {
    PERSONAL("개인채팅"),
    GROUP("그룹채팅"),
    NOTIFICATION("공지사항")
}