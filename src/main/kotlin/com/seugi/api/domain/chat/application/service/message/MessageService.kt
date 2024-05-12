package com.seugi.api.domain.chat.application.service.message

import com.seugi.api.domain.chat.domain.chat.embeddable.Emoji
import com.seugi.api.domain.chat.domain.chat.model.Message
import com.seugi.api.domain.chat.domain.chat.model.Type
import com.seugi.api.domain.chat.presentation.joined.dto.response.GetMessageResponse
import com.seugi.api.domain.chat.presentation.websocket.dto.ChatMessageDto
import com.seugi.api.global.response.BaseResponse
import org.springframework.data.domain.Pageable

interface MessageService {

    fun sendMessage(chatMessageDto: ChatMessageDto, userId: Long)
    fun toMessage(type: Type, chatRoomId: Long, eventList: MutableList<Long>, userId: Long)
    fun savaMessage(chatMessageDto: ChatMessageDto, userId: Long): Message
    fun getMessages(chatRoomId: Long, userId: Long, pageable: Pageable) : BaseResponse<GetMessageResponse>

    fun addEmojiToMessage(userId: Long, messageId: String, emoji: Emoji) : BaseResponse<Unit>
    fun deleteMessage(userId: Long, messageId: String) : BaseResponse<Unit>

    fun sub(userId: Long, roomId: String)
    fun unsub(userId: Long)

}