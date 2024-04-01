package seugi.server.domain.chat.domain.chat

import jakarta.persistence.Id
import org.springframework.data.mongodb.core.mapping.Document
import seugi.server.domain.chat.domain.chat.embeddable.Emoji
import seugi.server.domain.chat.domain.chat.embeddable.Read
import seugi.server.domain.chat.domain.chat.embeddable.UnRead
import seugi.server.domain.chat.domain.status.ChatStatusEnum
import java.time.LocalDateTime


@Document(collection = "messages")
class MessageEntity(

    @Id
    var chatRoomId: Long? = null,

    var writer: String,

    var message: String,

    var emoji: MutableList<Emoji> = mutableListOf(),

    var timestamp: LocalDateTime = LocalDateTime.now(),

    var read: MutableList<Read> = mutableListOf(),

    var unRead: MutableList<UnRead> = mutableListOf(),

    var messageStatus: ChatStatusEnum = ChatStatusEnum.ALIVE

)