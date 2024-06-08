package com.seugi.api.domain.chat.application.service.message

import com.seugi.api.domain.chat.domain.chat.embeddable.AddEmoji
import com.seugi.api.domain.chat.domain.chat.embeddable.DeleteMessage
import com.seugi.api.domain.chat.domain.chat.model.Message
import com.seugi.api.domain.chat.presentation.chat.member.dto.response.GetMessageResponse
import com.seugi.api.domain.chat.presentation.websocket.dto.ChatMessageDto
import com.seugi.api.domain.chat.presentation.websocket.dto.MessageEventDto
import com.seugi.api.global.response.BaseResponse
import org.springframework.data.domain.Pageable

interface MessageService {

    fun sendAndSaveMessage(chatMessageDto: ChatMessageDto, userId: Long)
    fun sendEventMessage(message: MessageEventDto, roomId: String)
    fun savaMessage(chatMessageDto: ChatMessageDto, userId: Long): Message
    fun getMessages(chatRoomId: String, userId: Long, pageable: Pageable): BaseResponse<GetMessageResponse>

    fun addEmojiToMessage(userId: Long, emoji: AddEmoji): BaseResponse<Unit>
    fun deleteEmojiToMessage(userId: Long, emoji: AddEmoji): BaseResponse<Unit>
    fun deleteMessage(userId: Long, deleteMessage: DeleteMessage): BaseResponse<Unit>

    fun sub(userId: Long, roomId: String)
    fun unsub(userId: Long)

}