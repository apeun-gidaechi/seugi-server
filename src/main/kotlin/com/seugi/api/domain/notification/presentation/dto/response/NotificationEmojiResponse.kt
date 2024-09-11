package com.seugi.api.domain.notification.presentation.dto.response

data class NotificationEmojiResponse(
    val emoji: String,
    val userList: List<Long>,
)
