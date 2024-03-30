package seugi.server.domain.chat.domain.room.model

import seugi.server.domain.chat.domain.status.ChatStatusEnum
import java.time.LocalDateTime

data class Room(
    val id: Long? = null,
    val chatName: String,
    val createdAt: LocalDateTime? = null,
    val chatStatusEnum: ChatStatusEnum? = null
)