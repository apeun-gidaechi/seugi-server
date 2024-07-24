package com.seugi.api.domain.chat.presentation.chat.member.dto.request

data class ChatMemberEventRequest(
    val chatRoomId: String = "",
    val chatMemberUsers: Set<Long> = emptySet(),
)