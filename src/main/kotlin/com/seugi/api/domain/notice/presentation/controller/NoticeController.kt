package com.seugi.api.domain.notice.presentation.controller

import com.seugi.api.domain.notice.presentation.dto.request.CreateNoticeRequest
import com.seugi.api.domain.notice.presentation.dto.request.UpdateNoticeRequest
import com.seugi.api.domain.notice.presentation.dto.response.NoticeResponse
import com.seugi.api.domain.notice.service.NoticeService
import com.seugi.api.global.common.annotation.GetAuthenticatedId
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/notice")
class NoticeController(
    private val noticeService: NoticeService,
) {

    @PostMapping
    fun createNotice(
        @RequestBody createNoticeRequest: CreateNoticeRequest,
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
    ): BaseResponse<List<NoticeResponse>> {
        return noticeService.getNotices(
            workspaceId = workspaceId,
            userId = userId
        )
    }

    @PatchMapping
    fun updateNotice(
        @RequestBody updateNoticeRequest: UpdateNoticeRequest,
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


}