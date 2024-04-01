package seugi.server.domain.chat.domain.chat.mapper

import org.springframework.stereotype.Component
import seugi.server.domain.chat.domain.chat.MessageEntity
import seugi.server.domain.chat.domain.chat.model.Message
import seugi.server.domain.chat.domain.joined.JoinedEntity
import seugi.server.domain.chat.presentation.websocket.dto.ChatMessageDto
import seugi.server.global.common.Mapper

@Component
class MessageMapper : Mapper<Message, MessageEntity> {

    override fun toDomain(entity: MessageEntity): Message {
        return Message(
            id = entity.id,
            chatRoomId = entity.chatRoomId!!,
            writer = entity.writer,
            userId = entity.userId,
            message = entity.message,
            emoji = entity.emoji.toMutableList(),
            timestamp = entity.timestamp.toString(),
            read = entity.read.toMutableList(),
            unRead = entity.unRead.toMutableList(),
            messageStatus = entity.messageStatus
        )
    }

    override fun toEntity(domain: Message): MessageEntity {
        return MessageEntity(
            chatRoomId = domain.chatRoomId,
            writer = domain.writer,
            userId = domain.userId,
            message = domain.message,
            unRead = domain.unRead.toMutableList(),
        )
    }

    fun toMessage(chatMessageDto: ChatMessageDto, joinedEntity: JoinedEntity, userId:Long, writer: String) : Message{
        return Message(
            chatRoomId = chatMessageDto.roomId,
            writer = writer,
            userId = userId,
            message = chatMessageDto.message,
            unRead = joinedEntity.joinedUserId
        )
    }

}