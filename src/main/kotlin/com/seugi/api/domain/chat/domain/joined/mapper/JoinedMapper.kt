package com.seugi.api.domain.chat.domain.joined.mapper

import org.springframework.stereotype.Component
import com.seugi.api.domain.chat.domain.enums.type.RoomType
import com.seugi.api.domain.chat.domain.joined.JoinedEntity
import com.seugi.api.domain.chat.domain.joined.model.Joined
import com.seugi.api.global.common.Mapper

@Component
class JoinedMapper : Mapper<Joined, JoinedEntity> {
    override fun toDomain(entity: JoinedEntity): Joined {
        return Joined(
            joinUserId = entity.joinedUserId,
            roomType = entity.roomType,
            roomAdmin = entity.roomAdmin,
            chatRoomId = entity.chatRoomId!!
        )
    }

    override fun toEntity(domain: Joined): JoinedEntity {
        return JoinedEntity(
            joinedUserId = domain.joinUserId,
            roomType = domain.roomType,
            roomAdmin = domain.roomAdmin,
            chatRoomId = domain.chatRoomId
        )
    }

    fun toEntity(chatRoomId : Long, joinedUserId : List<Long>, type: RoomType, roomAdmin: Long) : JoinedEntity{
        return JoinedEntity(
            joinedUserId = joinedUserId.toMutableSet(),
            roomType = type,
            roomAdmin = roomAdmin,
            chatRoomId = chatRoomId
        )
    }
}