package com.seugi.api.domain.notice.service

import com.seugi.api.domain.notice.presentation.dto.request.CreateNoticeRequest
import com.seugi.api.domain.notice.presentation.dto.request.UpdateNoticeRequest
import com.seugi.api.domain.notice.presentation.dto.response.NoticeResponse
import com.seugi.api.global.response.BaseResponse

interface NoticeService {

    fun createNotice(createNoticeRequest: CreateNoticeRequest, userId: Long): BaseResponse<Unit>

    fun getNotices(workspaceId: String, userId: Long): BaseResponse<List<NoticeResponse>>

    fun updateNotice(updateNoticeRequest: UpdateNoticeRequest, userId: Long): BaseResponse<Unit>

    fun deleteNotice(id: Long, workspaceId: String, userId: Long): BaseResponse<Unit>
}