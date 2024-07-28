package com.seugi.api.domain.notification.domain.embeddable

import jakarta.persistence.Embeddable

@Embeddable
data class NotificationEmoji(
    val emoji: String,
    val userId: Long,
)