package com.seugi.api.domain.chat.presentation.websocket.dto

import com.seugi.api.domain.chat.domain.chat.model.Type

data class ChatMessageDto(
    val type: Type? = Type.MESSAGE,
    val roomId: String? = null,
    val message: String? = null,
    val uuid: String? = null,
    val eventList: Set<Long>? = null,
    val emoticon: String? = null,
    val mention: Set<Long>? = null,
    val mentionAll: Boolean = false,
)
