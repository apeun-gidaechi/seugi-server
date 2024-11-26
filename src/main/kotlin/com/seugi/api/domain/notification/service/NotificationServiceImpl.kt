package com.seugi.api.domain.notification.service

import com.seugi.api.domain.member.adapter.out.repository.MemberRepository
import com.seugi.api.domain.notification.domain.NotificationEntity
import com.seugi.api.domain.notification.domain.NotificationRepository
import com.seugi.api.domain.notification.domain.embeddable.NotificationEmoji
import com.seugi.api.domain.notification.domain.mapper.NotificationMapper
import com.seugi.api.domain.notification.exception.NotificationErrorCode
import com.seugi.api.domain.notification.presentation.dto.request.CreateNotificationRequest
import com.seugi.api.domain.notification.presentation.dto.request.NotificationEmojiRequest
import com.seugi.api.domain.notification.presentation.dto.request.UpdateNotificationRequest
import com.seugi.api.domain.notification.presentation.dto.response.NotificationResponse
import com.seugi.api.domain.workspace.domain.entity.WorkspaceEntity
import com.seugi.api.domain.workspace.service.WorkspaceService
import com.seugi.api.global.exception.CustomException
import com.seugi.api.global.infra.fcm.FCMService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NotificationServiceImpl(
    private val memberRepository: MemberRepository,
    private val workspaceService: WorkspaceService,
    private val noticeRepository: NotificationRepository,
    private val noticeMapper: NotificationMapper,
    private val fcmService: FCMService,
    private val notificationCacheService: NotificationCacheService
) : NotificationService {

    @Transactional(readOnly = true)
    protected fun findNotificationById(notificationId: Long): NotificationEntity {
        return noticeRepository.findById(notificationId)
            .orElseThrow { CustomException(NotificationErrorCode.NOT_FOUND) }
    }

    @Transactional(readOnly = true)
    protected fun findWorkspaceById(workspaceId: String): WorkspaceEntity {
        return workspaceService.findWorkspaceById(workspaceId)
    }

    private fun sendAlert(workspaceEntity: WorkspaceEntity, userId: Long, message: String) {
        fcmService.sendNotificationAlert(workspaceEntity, userId, message)
    }

    @Transactional
    override fun createNotice(createNoticeRequest: CreateNotificationRequest, userId: Long) {
        val workspaceEntity = findWorkspaceById(createNoticeRequest.workspaceId)

        if (workspaceEntity.student.contains(userId)) throw CustomException(NotificationErrorCode.FORBIDDEN)

        val notice = noticeMapper.transferNoticeEntity(createNoticeRequest, memberRepository.findById(userId).get())
        noticeRepository.save(notice)

        sendAlert(workspaceEntity, userId, createNoticeRequest.title)
        notificationCacheService.deleteCache(createNoticeRequest.workspaceId)
    }

    @Transactional(readOnly = true)
    override fun getNotices(
        workspaceId: String,
        userId: Long,
        pageable: Pageable,
    ): List<NotificationResponse> {
        return notificationCacheService.getNotices(workspaceId, pageable)
    }

    @Transactional
    override fun updateNotice(updateNoticeRequest: UpdateNotificationRequest, userId: Long) {
        val noticeEntity = findNotificationById(updateNoticeRequest.id)

        if (noticeEntity.user!!.id != userId) throw CustomException(NotificationErrorCode.FORBIDDEN)

        noticeEntity.updateNotice(updateNoticeRequest)
        noticeRepository.save(noticeEntity)
        notificationCacheService.deleteCache(noticeEntity.workspaceId)
    }

    @Transactional
    override fun deleteNotice(id: Long, workspaceId: String, userId: Long) {
        val workspaceEntity = findWorkspaceById(workspaceId)
        val notice = findNotificationById(id)

        if (workspaceEntity.workspaceAdmin != userId &&
            !workspaceEntity.middleAdmin.contains(userId) &&
            notice.user!!.id != userId
        ) {
            throw CustomException(NotificationErrorCode.FORBIDDEN)
        }

        notice.deleteNotice()
        noticeRepository.save(notice)
        notificationCacheService.deleteCache(workspaceId)
    }

    private fun addEmoji(
        notification: NotificationEntity,
        userId: Long,
        notificationEmojiRequest: NotificationEmojiRequest,
    ) {
        notification.emoji.add(
            NotificationEmoji(
                emoji = notificationEmojiRequest.emoji,
                userId = userId
            )
        )
    }

    private fun removeEmoji(
        notification: NotificationEntity,
        matchEmoji: NotificationEmoji,
    ) {
        matchEmoji.let(notification.emoji::remove)
    }

    @Transactional
    override fun toggleEmoji(userId: Long, notificationEmojiRequest: NotificationEmojiRequest) {
        val notification = findNotificationById(notificationEmojiRequest.notificationId)

        val matchedEmoji = notification.emoji.find { it.emoji == notificationEmojiRequest.emoji && it.userId == userId }

        if (notification.id == notificationEmojiRequest.notificationId && matchedEmoji != null) {
            removeEmoji(notification, matchedEmoji)
        } else {
            addEmoji(notification, userId, notificationEmojiRequest)
        }

        noticeRepository.save(notification)
        notificationCacheService.deleteCache(notification.workspaceId)
    }
}