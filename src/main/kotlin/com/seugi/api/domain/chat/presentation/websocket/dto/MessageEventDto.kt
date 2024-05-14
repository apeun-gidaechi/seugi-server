package com.seugi.api.domain.chat.presentation.websocket.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.seugi.api.domain.chat.domain.chat.model.Type

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MessageEventDto(
    val type: Type? = null,
    val eventList: List<Long> = emptyList(),
    val messageId: String? = null,
    val emojiId: Long? = null
)
