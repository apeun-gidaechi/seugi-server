package com.seugi.api.domain.chat.presentation.room.dto.request

data class CreateRoomRequest(
    var roomName : String = "",
    val workspaceId: String,
    val joinUsers : MutableList<Long>? = emptyArray<Long>().toMutableList(),
    val chatRoomImg : String
)