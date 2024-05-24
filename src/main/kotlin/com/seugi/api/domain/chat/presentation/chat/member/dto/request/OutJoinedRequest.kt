package com.seugi.api.domain.chat.presentation.chat.member.dto.request

data class OutJoinedRequest(
    val roomId: Long? = null,
    val outJoinedUsers: List<Long> = emptyList()
)
