package com.seugi.api.domain.chat.application.service.message

import com.seugi.api.domain.chat.domain.chat.MessageEntity
import com.seugi.api.domain.chat.domain.chat.embeddable.AddEmoji
import com.seugi.api.domain.chat.domain.chat.embeddable.DeleteMessage
import com.seugi.api.domain.chat.domain.room.model.Room
import com.seugi.api.domain.chat.presentation.chat.member.dto.response.GetMessageResponse
import com.seugi.api.domain.chat.presentation.websocket.dto.ChatMessageDto
import com.seugi.api.domain.chat.presentation.websocket.dto.MessageEventDto
import com.seugi.api.global.response.BaseResponse
import java.time.LocalDateTime

interface MessageService {

    fun sendAndSaveMessage(chatMessageDto: ChatMessageDto, userId: Long)
    fun sendEventMessage(message: MessageEventDto, roomId: String)
    fun getLastMessage(roomId: String): MessageEntity?
    fun getMessages(chatRoomId: String, userId: Long, timestamp: LocalDateTime): BaseResponse<GetMessageResponse>
    fun getNotReadMessageCount(room: Room, userId: Long): Int

    fun addEmojiToMessage(userId: Long, emoji: AddEmoji): BaseResponse<Unit>
    fun deleteEmojiToMessage(userId: Long, emoji: AddEmoji): BaseResponse<Unit>
    fun deleteMessage(userId: Long, deleteMessage: DeleteMessage): BaseResponse<Unit>


}