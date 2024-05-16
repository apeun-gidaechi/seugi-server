package com.seugi.api.domain.chat.domain.chat.mapper

import org.springframework.stereotype.Component
import com.seugi.api.domain.chat.domain.chat.MessageEntity
import com.seugi.api.domain.chat.domain.chat.embeddable.MessageMember
import com.seugi.api.domain.chat.domain.chat.model.Message
import com.seugi.api.domain.chat.domain.joined.JoinedEntity
import com.seugi.api.domain.chat.presentation.websocket.dto.ChatMessageDto
import com.seugi.api.domain.member.adapter.out.entity.MemberEntity
import com.seugi.api.global.common.Mapper

@Component
class MessageMapper : Mapper<Message, MessageEntity> {


    override fun toDomain(entity: MessageEntity): Message {
        return Message(
            id = entity.id.toString(),
            type = entity.type,
            chatRoomId = entity.chatRoomId!!,
            author = entity.author,
            message = entity.message,
            eventList = entity.eventList,
            emoticon = entity.emoticon,
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
            eventList = domain.eventList,
            emoticon = domain.emoticon,
            mention = domain.mention,
            mentionALl = domain.mentionAll,
            joined = domain.joined
        )
    }

    fun toMessage(chatMessageDto: ChatMessageDto, joinedEntity: JoinedEntity, author: MemberEntity, readUsers:List<Long>) : Message{
        return Message(
            type = chatMessageDto.type!!,
            chatRoomId = chatMessageDto.roomId!!,
            author = toMember(author),
            message = chatMessageDto.message!!,
            eventList = chatMessageDto.eventList,
            emoticon = chatMessageDto.emoticon,
            mention = chatMessageDto.mention!!,
            mentionAll = chatMessageDto.mentionAll,
            joined = joinedEntity.joinedUserId,
            read = readUsers
        )
    }

     private fun toMember(entity: MemberEntity): MessageMember {
        return MessageMember (
            id =  entity.id!!,
            name = entity.name
        )
    }

}