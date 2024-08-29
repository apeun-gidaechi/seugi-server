package com.seugi.api.domain.notification.presentation.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.seugi.api.domain.notification.domain.embeddable.NotificationEmoji

@JsonInclude(JsonInclude.Include.NON_NULL)
data class NotificationResponse(
    val id: Long? = null,
    val workspaceId: String? = null,
    val userId: Long? = null,
    val userName: String? = null,
    val title: String? = null,
    val content: String? = null,
    val emoji: List<NotificationEmoji>? = null,
    val creationDate: String? = null,
    val lastModifiedDate: String? = null,
)