package seugi.server.domain.chat.domain.room.model

import seugi.server.domain.chat.domain.enums.status.ChatStatusEnum
import seugi.server.domain.chat.domain.enums.type.RoomType

data class Room(
    val id: Long? = null,
    val type: RoomType,
    val chatName: String,
    val containUserCnt : Long,
    val createdAt: String? = null,
    val chatStatusEnum: ChatStatusEnum? = null
)