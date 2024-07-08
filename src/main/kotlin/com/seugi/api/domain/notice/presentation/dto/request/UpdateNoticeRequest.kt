package com.seugi.api.domain.notice.presentation.dto.request

data class UpdateNoticeRequest(
    val id: Long,
    val title: String,
    val content: String,
)
