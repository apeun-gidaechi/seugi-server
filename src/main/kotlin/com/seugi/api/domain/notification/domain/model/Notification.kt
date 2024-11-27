package com.seugi.api.domain.notification.domain.model

import com.seugi.api.domain.member.domain.model.Member
import com.seugi.api.domain.notification.domain.embeddable.NotificationEmoji

data class Notification(
    val id: Long? = null,
    val workspaceId: String,
    val user: Member,
    val title: String,
    val content: String,
    val emoji: List<NotificationEmoji>? = null,
    val creationDate: String? = null,
    val lastModifiedDate: String? = null,
    val deleted: Boolean = false,
)