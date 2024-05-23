package com.seugi.api.domain.chat.presentation.websocket.dto

import com.seugi.api.domain.chat.domain.chat.embeddable.MessageUserId
import com.seugi.api.domain.chat.domain.chat.model.Type

data class ChatMessageDto(
    val type: Type? = Type.MESSAGE,
    val roomId: Long? = null,
    val message: String? = null,
    val eventList: MutableList<Long>? = null,
    val emoticon: String? = null,
    val mention: List<MessageUserId>? = emptyList(),
    val mentionAll: Boolean = false,
)
