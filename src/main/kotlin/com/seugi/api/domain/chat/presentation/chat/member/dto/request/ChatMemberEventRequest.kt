package com.seugi.api.domain.chat.presentation.chat.member.dto.request

data class ChatMemberEventRequest(
    val chatRoomId: String? = null,
    val chatMemberUsers: Set<Long> = emptySet()
)