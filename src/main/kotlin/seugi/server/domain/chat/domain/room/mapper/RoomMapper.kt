package seugi.server.domain.chat.domain.room.mapper

import org.springframework.stereotype.Component
import seugi.server.domain.chat.domain.room.ChatRoomEntity
import seugi.server.domain.chat.domain.room.model.Room
import seugi.server.global.common.Mapper

@Component
class RoomMapper: Mapper<Room, ChatRoomEntity> {
    override fun toDomain(entity: ChatRoomEntity): Room {
        return Room(
            id = entity.id!!,
            chatName = entity.chatName,
            createdAt = entity.createdAt,
            joined = entity.joined,
            chatStatusEnum = entity.chatStatus,
        )
    }

    override fun toEntity(domain: Room): ChatRoomEntity {
        return ChatRoomEntity(
            chatName = domain.chatName,
            joined = domain.joined

        )
    }
}