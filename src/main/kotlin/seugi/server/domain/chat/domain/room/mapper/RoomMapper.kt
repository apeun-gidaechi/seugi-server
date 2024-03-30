package seugi.server.domain.chat.domain.room.mapper

import org.springframework.stereotype.Component
import seugi.server.domain.chat.domain.joined.JoinedEntity
import seugi.server.domain.chat.domain.room.ChatRoomEntity
import seugi.server.domain.chat.domain.room.model.Room
import seugi.server.domain.chat.presentation.room.dto.request.CreateRoomRequest
import seugi.server.global.common.Mapper

@Component
class RoomMapper: Mapper<Room, ChatRoomEntity> {
    override fun toDomain(entity: ChatRoomEntity): Room {
        return Room(
            id = entity.id!!,
            chatName = entity.chatName,
            createdAt = entity.createdAt,
            chatStatusEnum = entity.chatStatus,
        )
    }

    override fun toEntity(domain: Room): ChatRoomEntity {
        return ChatRoomEntity(
            chatName = domain.chatName
        )
    }

    fun toRoom(createRoomRequest: CreateRoomRequest) : Room{
        return Room(
            chatName = createRoomRequest.roomName
        )
    }
}