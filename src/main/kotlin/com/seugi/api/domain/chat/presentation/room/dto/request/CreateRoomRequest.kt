package com.seugi.api.domain.chat.presentation.room.dto.request

data class CreateRoomRequest(
    var roomName: String = "",
    val workspaceId: String = "",
    val joinUsers: MutableSet<Long> = emptySet<Long>().toMutableSet(),
    val chatRoomImg: String = ""
)