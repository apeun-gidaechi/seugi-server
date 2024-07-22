package com.seugi.api.domain.notification.presentation.dto.request

data class UpdateNotificationRequest(
    val id: Long = -1,
    val title: String = "",
    val content: String = "",
)
