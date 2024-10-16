package com.seugi.api.domain.chat.domain.room.model

import com.seugi.api.domain.chat.domain.enums.status.ChatStatusEnum
import com.seugi.api.domain.chat.domain.enums.type.RoomType
import java.time.LocalDateTime

data class Room(
    val id: String? = null,
    val workspaceId: String,
    val type: RoomType,
    val roomAdmin: Long,
    val chatName: String,
    val chatRoomImg: String?,
    val createdAt: LocalDateTime,
    val chatStatusEnum: ChatStatusEnum? = null,
    val joinUserInfo: Set<JoinUserInfo>,
)