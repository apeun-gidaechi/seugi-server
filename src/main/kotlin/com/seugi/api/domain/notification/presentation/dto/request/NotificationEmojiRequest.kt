package com.seugi.api.domain.notification.presentation.dto.request

data class NotificationEmojiRequest(
    val emoji: String = "",
    val notificationId: Long = -1,
)