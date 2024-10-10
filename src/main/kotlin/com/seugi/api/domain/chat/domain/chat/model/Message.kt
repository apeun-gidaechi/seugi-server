package com.seugi.api.domain.chat.domain.chat.model

import com.seugi.api.domain.chat.domain.chat.embeddable.Emoji
import com.seugi.api.domain.chat.domain.enums.status.ChatStatusEnum

data class Message(
    val id: String? = null,
    val chatRoomId: String,
    val type: Type,
    val userId: Long,
    val message: String,
    val eventList: Set<Long>? = emptySet(),
    val emoticon: String?,
    val emojiList: List<Emoji> = MutableList(8) { Emoji(it + 1) },
    val mention: Set<Long> = emptySet(),
    val mentionAll: Boolean = false,
    val timestamp: String? = null,
    val messageStatus: ChatStatusEnum = ChatStatusEnum.ALIVE
)