package seugi.server.domain.chat.domain.chat

import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import seugi.server.domain.chat.domain.chat.embeddable.Emoji
import seugi.server.domain.chat.domain.chat.embeddable.MessageUserId
import seugi.server.domain.chat.domain.chat.model.Type
import seugi.server.domain.chat.domain.status.ChatStatusEnum
import seugi.server.domain.member.adapter.out.entity.MemberEntity
import java.time.LocalDateTime


@Document(collection = "messages")
data class MessageEntity(

    @Id
    val id: ObjectId? = null,

    var chatRoomId: Long? = null,

    @Enumerated(EnumType.ORDINAL)
    val type : Type,

    var author : MemberEntity,

    val message : String = "",

    val emojiList: MutableList<Emoji> = MutableList(8) { Emoji(it+1) },

    val mention : List<MessageUserId> = emptyList(),

    val mentionALl : Boolean = false,

    var timestamp: LocalDateTime = LocalDateTime.now(),

    var read: MutableList<Long> = mutableListOf(),

    var unRead: MutableList<Long>,

    var messageStatus: ChatStatusEnum = ChatStatusEnum.ALIVE

)