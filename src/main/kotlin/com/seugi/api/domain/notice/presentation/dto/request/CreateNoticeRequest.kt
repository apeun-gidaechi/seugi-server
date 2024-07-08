package com.seugi.api.domain.notice.presentation.dto.request

data class CreateNoticeRequest(
    val title: String = "",
    val content: String = "",
    val workspaceId: String = "",
)