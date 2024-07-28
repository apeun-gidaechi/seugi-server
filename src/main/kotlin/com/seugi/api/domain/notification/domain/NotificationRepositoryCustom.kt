package com.seugi.api.domain.notification.domain

import org.springframework.data.domain.Pageable

interface NotificationRepositoryCustom {
    fun findByWorkspaceId(workspaceId: String, pageable: Pageable): List<NotificationEntity>
}