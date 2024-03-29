package seugi.server.domain.chat.domain.chat.mapper

import seugi.server.domain.chat.domain.chat.MessageEntity
import seugi.server.domain.chat.domain.chat.model.Message
import seugi.server.domain.chat.domain.room.ChatRoomEntity
import seugi.server.global.common.Mapper

class MessageMapper : Mapper<Message, MessageEntity> {

    override fun toDomain(entity: MessageEntity): Message {
        return Message(
            id = entity.id!!,
            chatRoomId = entity.chatRoom.id!!,
            writer = entity.writer,
            message = entity.message,
            emoji = entity.emoji.toMutableList(),
            timestamp = entity.timestamp,
            read = entity.read.toMutableList(),
            unRead = entity.unRead.toMutableList(),
            messageStatus = entity.messageStatus.toString()
        )
    }

    private var chatRoomEntity : ChatRoomEntity? = null

    override fun toEntity(domain: Message): MessageEntity {
        return MessageEntity(
            chatRoom = this.chatRoomEntity!! ,
            writer = domain.writer,
            message = domain.message
        )
    }
    fun setChatRoom(chatRoomEntity: ChatRoomEntity){
        this.chatRoomEntity = chatRoomEntity
    }
}