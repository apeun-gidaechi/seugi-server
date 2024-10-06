package com.seugi.api.domain.chat.domain.room.model

import java.time.LocalDateTime

data class JoinUserInfo(
    val userId: Long,
    var timestamp: LocalDateTime,
)