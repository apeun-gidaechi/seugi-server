package seugi.server.domain.chat.domain.chat.model

import seugi.server.domain.chat.domain.chat.embeddable.Emoji
import seugi.server.domain.chat.domain.chat.embeddable.MessageMember
import seugi.server.domain.chat.domain.chat.embeddable.MessageUserId
import seugi.server.domain.chat.domain.enums.status.ChatStatusEnum

data class Message(
    val id : String? = null,
    val chatRoomId: Long,
    val type : Type,
    val author : MessageMember,
    val message: String,
    val emojiList: MutableList<Emoji> = MutableList(8) { Emoji(it+1) },
    val mention : List<MessageUserId> = emptyList(),
    val mentionAll : Boolean = false,
    val timestamp: String? = null,
    val read: List<Long> = emptyList(),
    val joined: Set<Long>,
    val messageStatus: ChatStatusEnum = ChatStatusEnum.ALIVE
)