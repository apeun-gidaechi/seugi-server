package com.seugi.api.domain.chat.domain.chat.model

import com.seugi.api.domain.chat.domain.chat.embeddable.Emoji
import com.seugi.api.domain.chat.domain.chat.embeddable.MessageUserId
import com.seugi.api.domain.chat.domain.enums.status.ChatStatusEnum

data class Message(
    val id: String? = null,
    val chatRoomId: String,
    val type: Type,
    val author: Long,
    val message: String,
    val eventList: Set<Long>? = emptySet(),
    val emoticon: String?,
    val emojiList: List<Emoji> = MutableList(8) { Emoji(it + 1) },
    val mention: List<MessageUserId> = emptyList(),
    val mentionAll: Boolean = false,
    val timestamp: String? = null,
    val read: List<Long> = emptyList(),
    val messageStatus: ChatStatusEnum = ChatStatusEnum.ALIVE
)