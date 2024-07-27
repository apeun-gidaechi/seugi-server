package com.seugi.api.domain.notification.presentation.controller

import com.seugi.api.domain.notification.presentation.dto.request.CreateNotificationRequest
import com.seugi.api.domain.notification.presentation.dto.request.NotificationEmojiRequest
import com.seugi.api.domain.notification.presentation.dto.request.UpdateNotificationRequest
import com.seugi.api.domain.notification.presentation.dto.response.NotificationResponse
import com.seugi.api.domain.notification.service.NotificationService
import com.seugi.api.global.common.annotation.GetAuthenticatedId
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/notification")
class NotificationController(
    private val noticeService: NotificationService,
) {

    @PostMapping
    fun createNotice(
        @RequestBody createNoticeRequest: CreateNotificationRequest,
        @GetAuthenticatedId userId: Long,
    ): BaseResponse<Unit> {
        return noticeService.createNotice(
            createNoticeRequest = createNoticeRequest,
            userId = userId
        )
    }

    @GetMapping("/{workspaceId}")
    fun getNotices(
        @PathVariable workspaceId: String,
        @GetAuthenticatedId userId: Long,
    ): BaseResponse<List<NotificationResponse>> {
        return noticeService.getNotices(
            workspaceId = workspaceId,
            userId = userId
        )
    }

    @PatchMapping
    fun updateNotice(
        @RequestBody updateNoticeRequest: UpdateNotificationRequest,
        @GetAuthenticatedId userId: Long,
    ): BaseResponse<Unit> {
        return noticeService.updateNotice(
            updateNoticeRequest = updateNoticeRequest,
            userId = userId
        )
    }

    @DeleteMapping("/{workspaceId}/{id}")
    fun deleteNotice(
        @PathVariable workspaceId: String,
        @PathVariable id: Long,
        @GetAuthenticatedId userId: Long,
    ): BaseResponse<Unit> {
        return noticeService.deleteNotice(
            id = id,
            workspaceId = workspaceId,
            userId = userId
        )
    }

    @PatchMapping("/emoji")
    fun addEmoji(
        @GetAuthenticatedId userId: Long,
        @RequestBody notificationEmojiRequest: NotificationEmojiRequest,
    ): BaseResponse<Unit> {
        return noticeService.toggleEmoji(userId, notificationEmojiRequest)
    }


}