package com.seugi.api.domain.chat.presentation.joined.dto.request

data class AddJoinedRequest(
    val chatRoomId : Long? = null,
    val joinUserIds : List<Long> = emptyList()
)