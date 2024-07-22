package com.seugi.api.domain.notification.presentation.dto.request

data class CreateNotificationRequest(
    val title: String = "",
    val content: String = "",
    val workspaceId: String = "",
)