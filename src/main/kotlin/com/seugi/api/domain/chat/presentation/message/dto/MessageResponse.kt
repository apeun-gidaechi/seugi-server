package com.seugi.api.domain.chat.presentation.message.dto

import com.seugi.api.domain.chat.domain.chat.embeddable.Emoji
import com.seugi.api.domain.chat.domain.chat.model.Type
import com.seugi.api.domain.chat.domain.enums.status.ChatStatusEnum

data class MessageResponse(
    val id: String,
    val chatRoomId: String,
    val type: Type,
    val userId: Long,
    val message: String,
    val uuid: String,
    val eventList: Set<Long>,
    val emoticon: String?,
    val emojiList: List<Emoji>,
    val mention: Set<Long> = emptySet(),
    val mentionAll: Boolean,
    val timestamp: String,
    val read: List<Long>,
    val messageStatus: ChatStatusEnum,
)