package seugi.server.domain.chat.domain.chat.mapper

import org.springframework.stereotype.Component
import seugi.server.domain.chat.domain.chat.MessageEntity
import seugi.server.domain.chat.domain.chat.model.Message
import seugi.server.global.common.Mapper

@Component
class MessageMapper : Mapper<Message, MessageEntity> {

    override fun toDomain(entity: MessageEntity): Message {
        return Message(
            chatRoomId = entity.chatRoomId!!,
            writer = entity.writer,
            message = entity.message,
            emoji = entity.emoji.toMutableList(),
            timestamp = entity.timestamp,
            read = entity.read.toMutableList(),
            unRead = entity.unRead.toMutableList(),
            messageStatus = entity.messageStatus.toString()
        )
    }

    override fun toEntity(domain: Message): MessageEntity {
        return MessageEntity(
            chatRoomId = domain.chatRoomId,
            writer = domain.writer,
            message = domain.message
        )
    }

}