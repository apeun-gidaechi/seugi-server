package com.seugi.api.domain.chat.domain.joined.model

import com.seugi.api.domain.chat.domain.enums.type.RoomType

data class Joined (
    val chatRoomId : Long,
    val workspaceId: String,
    val roomType: RoomType,
    val roomAdmin: Long,
    val joinUserId : MutableSet<Long>,
)