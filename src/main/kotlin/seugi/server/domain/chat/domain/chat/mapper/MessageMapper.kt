package seugi.server.domain.chat.domain.chat.mapper

import org.springframework.stereotype.Component
import seugi.server.domain.chat.domain.chat.MessageEntity
import seugi.server.domain.chat.domain.chat.embeddable.MessageMember
import seugi.server.domain.chat.domain.chat.model.Message
import seugi.server.domain.chat.domain.joined.JoinedEntity
import seugi.server.domain.chat.presentation.websocket.dto.ChatMessageDto
import seugi.server.domain.member.adapter.out.entity.MemberEntity
import seugi.server.global.common.Mapper

@Component
class MessageMapper : Mapper<Message, MessageEntity> {


    override fun toDomain(entity: MessageEntity): Message {
        return Message(
            id = entity.id.toString(),
            type = entity.type,
            chatRoomId = entity.chatRoomId!!,
            author = entity.author,
            message = entity.message,
            emojiList = entity.emojiList,
            timestamp = entity.timestamp.toString(),
            mention = entity.mention,
            mentionAll = entity.mentionALl,
            read = entity.read.toMutableList(),
            joined = entity.joined,
            messageStatus = entity.messageStatus
        )
    }

    override fun toEntity(domain: Message): MessageEntity {
        return MessageEntity(
            type = domain.type,
            chatRoomId = domain.chatRoomId,
            author = domain.author,
            message = domain.message,
            mention = domain.mention,
            mentionALl = domain.mentionAll,
            joined = domain.joined
        )
    }

    fun toMessage(chatMessageDto: ChatMessageDto, joinedEntity: JoinedEntity, author: MemberEntity) : Message{
        return Message(
            type = chatMessageDto.type!!,
            chatRoomId = chatMessageDto.roomId!!,
            author = toMember(author),
            message = chatMessageDto.message!!,
            mention = chatMessageDto.mention!!,
            mentionAll = chatMessageDto.mentionAll,
            joined = joinedEntity.joinedUserId
        )
    }

     fun toMember(entity: MemberEntity): MessageMember {
        return MessageMember (
            id =  entity.id,
            name = entity.name
        )
    }

//
//    fun toJoined(entity: JoinedEntity) :Joined{
//        return Joined(
//            chatRoomId = entity.chatRoomId!!,
//            joinUserId = entity.joinedUserId
//        )
//    }


}