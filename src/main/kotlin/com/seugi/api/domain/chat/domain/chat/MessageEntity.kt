package com.seugi.api.domain.chat.domain.chat

import com.seugi.api.domain.chat.domain.chat.embeddable.Emoji
import com.seugi.api.domain.chat.domain.chat.embeddable.MessageUserId
import com.seugi.api.domain.chat.domain.chat.model.Type
import com.seugi.api.domain.chat.domain.enums.status.ChatStatusEnum
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime


@Document(collection = "messages")
class MessageEntity(

    @Id
    val id: ObjectId? = null,

    val chatRoomId: String? = null,

    @Enumerated(EnumType.ORDINAL)
    val type: Type,

    val author: Long,

    val message: String = "",

    val eventList: Set<Long>?,

    val emoticon: String?,

    val emojiList: List<Emoji> = MutableList(8) { Emoji(it + 1) },

    val mention: List<MessageUserId> = emptyList(),

    val mentionALl: Boolean = false,

    var timestamp: LocalDateTime = LocalDateTime.now(),

    val read: MutableSet<Long> = mutableSetOf(),

    var messageStatus: ChatStatusEnum = ChatStatusEnum.ALIVE

)