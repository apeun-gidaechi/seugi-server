package com.seugi.api.domain.notification.domain

import org.springframework.data.repository.CrudRepository

interface NotificationRepository : CrudRepository<NotificationEntity, Long>, NotificationRepositoryCustom {
    override fun findByWorkspaceId(workspaceId: String): List<NotificationEntity>
}