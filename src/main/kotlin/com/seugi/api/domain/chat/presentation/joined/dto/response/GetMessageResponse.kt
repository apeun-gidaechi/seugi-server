package com.seugi.api.domain.chat.presentation.joined.dto.response

import com.seugi.api.domain.chat.domain.chat.model.Message

data class GetMessageResponse(
    val firstMessageId: String?,
    val messages: List<Message>
)
