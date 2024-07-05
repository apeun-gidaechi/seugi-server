package com.seugi.api.domain.notice.presentation.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class NoticeResponse(
    val id: Long? = null,
    val workspaceId: String? = null,
    val userName: String? = null,
    val title: String? = null,
    val content: String? = null,
    val emoji: List<String>? = null,
    val creationDate: String? = null,
    val lastModifiedDate: String? = null,
)