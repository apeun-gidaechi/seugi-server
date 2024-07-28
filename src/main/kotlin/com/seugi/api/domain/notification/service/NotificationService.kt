package com.seugi.api.domain.notification.service

import com.seugi.api.domain.notification.presentation.dto.request.CreateNotificationRequest
import com.seugi.api.domain.notification.presentation.dto.request.NotificationEmojiRequest
import com.seugi.api.domain.notification.presentation.dto.request.UpdateNotificationRequest
import com.seugi.api.domain.notification.presentation.dto.response.NotificationResponse
import com.seugi.api.global.response.BaseResponse
import org.springframework.data.domain.Pageable

interface NotificationService {

    fun createNotice(createNoticeRequest: CreateNotificationRequest, userId: Long): BaseResponse<Unit>

    fun getNotices(workspaceId: String, userId: Long, pageable: Pageable): BaseResponse<List<NotificationResponse>>

    fun updateNotice(updateNoticeRequest: UpdateNotificationRequest, userId: Long): BaseResponse<Unit>

    fun deleteNotice(id: Long, workspaceId: String, userId: Long): BaseResponse<Unit>

    fun toggleEmoji(userId: Long, notificationEmojiRequest: NotificationEmojiRequest): BaseResponse<Unit>
}