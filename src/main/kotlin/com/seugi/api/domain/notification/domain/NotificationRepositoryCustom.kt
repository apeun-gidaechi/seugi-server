package com.seugi.api.domain.notification.domain

interface NotificationRepositoryCustom {
    fun findByWorkspaceId(workspaceId: String): List<NotificationEntity>
}