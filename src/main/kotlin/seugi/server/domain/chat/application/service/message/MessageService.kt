package seugi.server.domain.chat.application.service.message

import seugi.server.domain.chat.presentation.websocket.dto.ChatMessageDto

interface MessageService {
    fun saveMessage(chatMessageDto: ChatMessageDto)
}