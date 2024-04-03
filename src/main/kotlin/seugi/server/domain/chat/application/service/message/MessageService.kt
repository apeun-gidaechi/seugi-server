package seugi.server.domain.chat.application.service.message

import seugi.server.domain.chat.domain.chat.embeddable.Emoji
import seugi.server.domain.chat.domain.chat.model.Message
import seugi.server.domain.chat.presentation.websocket.dto.ChatMessageDto
import seugi.server.global.response.BaseResponse

interface MessageService {

    fun saveMessage(chatMessageDto: ChatMessageDto, userId: Long) : Message
    fun getMessages(chatRoomId: Long, userId: Long) : BaseResponse<List<Message>>

    fun readMessage(userId: Long, chatRoomId: Long) : BaseResponse<Unit>
    fun addEmojiToMessage(userId: Long, messageId: String, emoji: Emoji) : BaseResponse<Unit>
    fun deleteMessage(userId: Long, messageId: String) : BaseResponse<Unit>

}