package seugi.server.domain.chat.domain.chat

import jakarta.persistence.Id
import org.springframework.data.mongodb.core.mapping.Document
import seugi.server.domain.chat.domain.chat.embeddable.Emoji
import seugi.server.domain.chat.domain.status.ChatStatusEnum
import java.time.LocalDateTime


@Document(collection = "messages")
class MessageEntity(

    @Id
    val id: String? = null,

    var chatRoomId: Long? = null,

    var writer: String,

    var userId: Long,

    var message: String,

    var emoji: MutableList<Emoji> = mutableListOf(),

    var timestamp: LocalDateTime = LocalDateTime.now(),

    var read: MutableList<Long> = mutableListOf(),

    var unRead: MutableList<Long>,

    var messageStatus: ChatStatusEnum = ChatStatusEnum.ALIVE

)