package seugi.server.domain.chat.domain.chat.model

import seugi.server.domain.chat.domain.chat.embeddable.Emoji
import seugi.server.domain.chat.domain.status.ChatStatusEnum

data class Message(
    val id : String? = null,
    val chatRoomId: Long,
    val writer: String,
    val userId: Long,
    val message: String,
    val emoji: List<Emoji> = emptyList(),
    val timestamp: String? = null,
    val read: List<Long> = emptyList(),
    val unRead: List<Long>,
    val messageStatus: ChatStatusEnum = ChatStatusEnum.ALIVE
)