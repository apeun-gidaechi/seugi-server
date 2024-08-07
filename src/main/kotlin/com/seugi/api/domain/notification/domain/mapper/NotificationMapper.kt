package com.seugi.api.domain.notification.domain.mapper

import com.seugi.api.domain.member.adapter.out.entity.MemberEntity
import com.seugi.api.domain.notification.domain.NotificationEntity
import com.seugi.api.domain.notification.domain.model.Notification
import com.seugi.api.domain.notification.presentation.dto.request.CreateNotificationRequest
import com.seugi.api.domain.notification.presentation.dto.response.NotificationResponse
import com.seugi.api.global.common.Mapper
import org.springframework.stereotype.Component

@Component
class NotificationMapper : Mapper<Notification, NotificationEntity> {
    override fun toDomain(entity: NotificationEntity): Notification {
        return Notification(
            id = entity.id!!,
            workspaceId = entity.workspaceId,
            user = entity.user!!,
            title = entity.title,
            content = entity.content,
            emoji = entity.emoji,
            creationDate = entity.creationDate.toString(),
            lastModifiedDate = entity.creationDate.toString(),
            deleted = entity.deleted
        )
    }

    override fun toEntity(domain: Notification): NotificationEntity {
        return NotificationEntity(
            workspaceId = domain.workspaceId,
            user = domain.user,
            title = domain.title,
            content = domain.content
        )
    }

    fun transferNoticeEntity(
        createNoticeRequest: CreateNotificationRequest,
        userEntity: MemberEntity,
    ): NotificationEntity {
        return toEntity(
            Notification(
                workspaceId = createNoticeRequest.workspaceId,
                user = userEntity,
                title = createNoticeRequest.title,
                content = createNoticeRequest.content
            )
        )
    }

    fun transferNoticeResponse(notice: Notification): NotificationResponse {
        return NotificationResponse(
            id = notice.id,
            workspaceId = notice.workspaceId,
            userName = notice.user.name,
            title = notice.title,
            content = notice.content,
            emoji = notice.emoji,
            creationDate = notice.creationDate.toString(),
            lastModifiedDate = notice.lastModifiedDate.toString()
        )
    }


}