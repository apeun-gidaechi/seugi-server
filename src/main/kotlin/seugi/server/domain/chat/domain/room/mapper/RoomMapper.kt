package seugi.server.domain.chat.domain.room.mapper

import org.springframework.stereotype.Component
import seugi.server.domain.chat.domain.joined.JoinedEntity
import seugi.server.domain.chat.domain.joined.model.Joined
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
            joined = entity.joined.map { toJoinedDomain(it) }.toMutableList(),
            chatStatusEnum = entity.chatStatus,
        )
    }

    override fun toEntity(domain: Room): ChatRoomEntity {
        val chatRoomEntity = ChatRoomEntity(
            chatName = domain.chatName,
        )
        chatRoomEntity.joined = domain.joined.map { doEntity(it, chatRoomEntity) }.toMutableList()

        return chatRoomEntity
    }

    fun toRoom(createRoomRequest: CreateRoomRequest) : Room{
        return Room(
            chatName = createRoomRequest.roomName,
            joined = createRoomRequest.joinUsers.toMutableList()
        )
    }

    private fun toJoinedDomain(joined: JoinedEntity) : Joined{
        return Joined(
            joinUserId = joined.joinedUserId!!,
            chatRoomId = joined.chatRoom?.id!!
        )
    }

    private fun doEntity(joined: Joined, chatRoomEntity: ChatRoomEntity) : JoinedEntity{
        return JoinedEntity(
            joinedUserId = joined.joinUserId,
            chatRoom = chatRoomEntity
        )
    }
}