package com.seugi.api.domain.chat.presentation.joined.dto.request

data class OutJoinedRequest(
    val roomId: Long? = null,
    val outJoinedUsers: List<Long> = emptyList()
)
