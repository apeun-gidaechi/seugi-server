package com.seugi.api.domain.chat.domain.chat.mapper

import com.seugi.api.domain.chat.domain.chat.MessageEntity
import com.seugi.api.domain.chat.domain.chat.model.Message
import com.seugi.api.domain.chat.domain.enums.status.ChatStatusEnum
import com.seugi.api.domain.chat.presentation.message.dto.MessageResponse
import com.seugi.api.domain.chat.presentation.websocket.dto.ChatMessageDto
import com.seugi.api.global.common.Mapper
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class MessageMapper : Mapper<Message, MessageEntity> {

    override fun toDomain(entity: MessageEntity): Message {
        return Message(
            id = entity.id.toString(),
            type = entity.type,
            chatRoomId = entity.chatRoomId!!,
            userId = entity.userId,
            message = if (entity.messageStatus == ChatStatusEnum.DELETE) "" else entity.message,
            eventList = entity.eventList,
            emoticon = entity.emoticon,
            emojiList = entity.emojiList,
            timestamp = entity.timestamp.toString(),
            mention = entity.mention,
            mentionAll = entity.mentionALl,
            messageStatus = entity.messageStatus
        )
    }

    override fun toEntity(domain: Message): MessageEntity {
        return MessageEntity(
            type = domain.type,
            chatRoomId = domain.chatRoomId,
            userId = domain.userId,
            message = domain.message,
            eventList = domain.eventList,
            emoticon = domain.emoticon,
            mention = domain.mention,
            mentionALl = domain.mentionAll
        )
    }

    fun toMessage(
        chatMessageDto: ChatMessageDto,
        author: Long,
    ): Message {
        return Message(
            type = chatMessageDto.type!!,
            chatRoomId = chatMessageDto.roomId!!,
            userId = author,
            message = chatMessageDto.message ?: "",
            eventList = chatMessageDto.eventList,
            emoticon = chatMessageDto.emoticon,
            mention = chatMessageDto.mention ?: emptySet(),
            mentionAll = chatMessageDto.mentionAll
        )
    }

    fun toMessageResponse(message: Message, uuid: String?): MessageResponse {
        return MessageResponse(
            id = message.id!!,
            chatRoomId = message.chatRoomId,
            type = message.type,
            userId = message.userId,
            message = message.message,
            uuid = uuid ?: "",
            eventList = message.eventList ?: emptySet(),
            emojiList = message.emojiList,
            emoticon = message.emoticon,
            mention = message.mention,
            mentionAll = message.mentionAll,
            timestamp = message.timestamp ?: LocalDateTime.now().toString(),
            messageStatus = message.messageStatus,
        )
    }

}