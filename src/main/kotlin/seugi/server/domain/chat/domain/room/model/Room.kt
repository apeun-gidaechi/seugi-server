package seugi.server.domain.chat.domain.room.model

import seugi.server.domain.chat.domain.chat.MessageEntity
import seugi.server.domain.chat.domain.joined.JoinedEntity
import seugi.server.domain.chat.domain.status.ChatStatusEnum
import java.time.LocalDateTime

data class Room(
    val id: Long,
    val chatName: String,
    val createdAt: LocalDateTime,
    val chatStatusEnum: ChatStatusEnum,
    val joined: MutableList<JoinedEntity>
)