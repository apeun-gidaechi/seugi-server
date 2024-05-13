package com.seugi.api.domain.chat.presentation.websocket.dto

import com.seugi.api.domain.chat.domain.chat.model.Type

data class MessageEventDto(
    val type: Type = Type.SUB,
    val subUserId: Long? = null
)
