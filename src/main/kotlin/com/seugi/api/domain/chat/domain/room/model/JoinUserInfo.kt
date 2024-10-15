package com.seugi.api.domain.chat.domain.room.model

import java.time.LocalDateTime

data class JoinUserInfo(
    val userId: Long,
    var timestamp: LocalDateTime,
) {
    val checkDate: String
        get() = timestamp.toString().substring(0, 4)
}