package com.seugi.api.domain.chat.domain.room.model

import com.seugi.api.domain.chat.domain.enums.status.ChatStatusEnum
import com.seugi.api.domain.chat.domain.enums.type.RoomType

data class Room(
    val id: String? = null,
    val workspaceID: String,
    val type: RoomType,
    val roomAdmin: Long,
    val chatName: String,
    val chatRoomImg: String,
    val createdAt: String? = null,
    val chatStatusEnum: ChatStatusEnum? = null,
    val joinUserId: Set<Long>
)