package com.seugi.api.domain.notice.presentation.dto.request

data class UpdateNoticeRequest(
    val id: Long = -1,
    val title: String = "",
    val content: String = "",
)
