package com.seugi.api.domain.chat.application.service.message

import com.seugi.api.domain.chat.domain.chat.embeddable.Emoji
import com.seugi.api.domain.chat.domain.chat.model.Message
import com.seugi.api.domain.chat.presentation.websocket.dto.ChatMessageDto
import com.seugi.api.global.response.BaseResponse

interface MessageService {

    fun sendMessage(chatMessageDto: ChatMessageDto, userId: Long)
    fun savaMessage(chatMessageDto: ChatMessageDto, userId: Long): Message
    fun getMessages(chatRoomId: Long, userId: Long) : BaseResponse<MutableMap<String, Any>>

    fun addEmojiToMessage(userId: Long, messageId: String, emoji: Emoji) : BaseResponse<Unit>
    fun deleteMessage(userId: Long, messageId: String) : BaseResponse<Unit>

}