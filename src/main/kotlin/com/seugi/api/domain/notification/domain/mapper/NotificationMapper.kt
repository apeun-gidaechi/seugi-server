package com.seugi.api.domain.notification.domain.mapper

import com.seugi.api.domain.member.domain.MemberEntity
import com.seugi.api.domain.member.domain.mapper.MemberMapper
import com.seugi.api.domain.notification.domain.NotificationEntity
import com.seugi.api.domain.notification.domain.embeddable.NotificationEmoji
import com.seugi.api.domain.notification.domain.model.Notification
import com.seugi.api.domain.notification.presentation.dto.request.CreateNotificationRequest
import com.seugi.api.domain.notification.presentation.dto.response.NotificationEmojiResponse
import com.seugi.api.domain.notification.presentation.dto.response.NotificationResponse
import com.seugi.api.global.common.Mapper
import org.springframework.stereotype.Component

@Component
class NotificationMapper (
    private val memberMapper: MemberMapper
) : Mapper<Notification, NotificationEntity> {
    override fun toDomain(entity: NotificationEntity): Notification {
        return Notification(
            id = entity.id!!,
            workspaceId = entity.workspaceId,
            user = memberMapper.toDomain(entity.user!!),
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
            user = memberMapper.toEntity(domain.user),
            title = domain.title,
            content = domain.content
        )
    }

    // todo: refactor
    fun transferNoticeEntity(
        createNoticeRequest: CreateNotificationRequest,
        userEntity: MemberEntity,
    ): NotificationEntity {
        return toEntity(
            Notification(
                workspaceId = createNoticeRequest.workspaceId,
                user = memberMapper.toDomain(userEntity),
                title = createNoticeRequest.title,
                content = createNoticeRequest.content
            )
        )
    }

    fun transferNoticeResponse(notification: Notification): NotificationResponse {
        return NotificationResponse(
            id = notification.id,
            workspaceId = notification.workspaceId,
            userId = notification.user.id,
            userName = notification.user.name,
            title = notification.title,
            content = notification.content,
            emoji = toNotificationEmojiResponse(notification.emoji),
            creationDate = notification.creationDate.toString(),
            lastModifiedDate = notification.lastModifiedDate.toString()
        )
    }

    private fun toNotificationEmojiResponse(emojiList: List<NotificationEmoji>?): List<NotificationEmojiResponse>? {

        val emojiGroup = emojiList?.groupBy { it.emoji }

        return emojiGroup?.map { (emoji, emojiUsers) ->
            NotificationEmojiResponse(
                emoji = emoji,
                userList = emojiUsers.map { it.userId }
            )
        }
    }


}