package com.seugi.api.global.infra.fcm

enum class FCMEnums(
    val type: String,
) {
    CHAT("채팅"),
    NOTIFICATION("공지사항")
}