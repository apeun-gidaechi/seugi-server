package seugi.server.domain.chat.domain.chat.model

import seugi.server.domain.chat.domain.chat.embeddable.Emoji
import seugi.server.domain.chat.domain.chat.embeddable.Read
import seugi.server.domain.chat.domain.chat.embeddable.UnRead
import java.time.LocalDateTime

data class Message(
    val id: Long,
    val chatRoomId: Long,
    val writer: String,
    val message: String,
    val emoji: List<Emoji>,
    val timestamp: LocalDateTime?,
    val read: List<Read>,
    val unRead: List<UnRead>,
    val messageStatus: String
)