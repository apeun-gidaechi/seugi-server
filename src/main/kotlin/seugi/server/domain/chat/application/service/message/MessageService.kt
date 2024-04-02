package seugi.server.domain.chat.application.service.message

import seugi.server.domain.chat.domain.chat.model.Message
import seugi.server.domain.chat.presentation.websocket.dto.ChatMessageDto
import seugi.server.global.response.BaseResponse

interface MessageService {
    fun saveMessage(chatMessageDto: ChatMessageDto)
    fun getMessages(chatRoomId: Long, userId: Long) : BaseResponse<List<Message>>
}