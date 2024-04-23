package com.seugi.api.domain.chat.presentation.room.dto.request

data class SearchRoomRequest(
    val workspaceId: String = "",
    val word: String = "",
)
