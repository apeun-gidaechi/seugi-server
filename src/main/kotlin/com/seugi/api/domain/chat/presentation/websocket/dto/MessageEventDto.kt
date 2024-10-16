package com.seugi.api.domain.chat.presentation.websocket.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.seugi.api.domain.chat.domain.chat.model.Type

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MessageEventDto(
    val type: Type? = null,
    val userId: Long? = null,
    val eventList: List<Long>? = null,
    val messageId: String? = null,
    val emojiId: Int? = null
)
