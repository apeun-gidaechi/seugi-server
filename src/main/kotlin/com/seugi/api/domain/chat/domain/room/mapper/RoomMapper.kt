package com.seugi.api.domain.chat.domain.room.mapper

import com.seugi.api.domain.chat.domain.enums.type.RoomType
import com.seugi.api.domain.chat.domain.enums.type.RoomType.GROUP
import com.seugi.api.domain.chat.domain.room.ChatRoomEntity
import com.seugi.api.domain.chat.domain.room.model.Room
import com.seugi.api.domain.chat.presentation.chat.room.dto.request.CreateRoomRequest
import com.seugi.api.domain.chat.presentation.chat.room.dto.response.RoomResponse
import com.seugi.api.domain.member.adapter.`in`.dto.res.RetrieveMemberResponse
import com.seugi.api.global.common.Mapper
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class RoomMapper : Mapper<Room, ChatRoomEntity> {
    override fun toDomain(entity: ChatRoomEntity): Room {
        return Room(
            id = entity.id!!.toString(),
            workspaceId = entity.workspaceId,
            type = entity.roomType,
            roomAdmin = entity.roomAdmin,
            chatName = entity.chatName,
            chatRoomImg = entity.chatRoomImg,
            createdAt = entity.createdAt,
            joinUserId = entity.joinedUserId,
            chatStatusEnum = entity.chatStatus
        )
    }

    override fun toEntity(domain: Room): ChatRoomEntity {
        return ChatRoomEntity(
            workspaceId = domain.workspaceId,
            chatName = domain.chatName,
            roomAdmin = domain.roomAdmin,
            roomType = domain.type,
            createdAt = domain.createdAt,
            joinedUserId = domain.joinUserId
        )
    }

    fun toRoom(createRoomRequest: CreateRoomRequest, type: RoomType, userId: Long): Room {
        createRoomRequest.joinUsers.add(userId)
        return Room(
            workspaceId = createRoomRequest.workspaceId,
            chatName = createRoomRequest.roomName,
            type = type,
            roomAdmin = if (type == GROUP) userId else -1,
            chatRoomImg = createRoomRequest.chatRoomImg,
            createdAt = LocalDateTime.now(),
            joinUserId = createRoomRequest.joinUsers
        )
    }

    fun toResponse(
        room: Room,
        members: Set<RetrieveMemberResponse>,
        lastMessage: String,
        lastMessageTimeStamp: String
    ): RoomResponse {
        return RoomResponse(
            id = room.id!!,
            workspaceId = room.workspaceId,
            roomAdmin = room.roomAdmin,
            chatName = room.chatName,
            chatRoomImg = room.chatRoomImg,
            createdAt = room.createdAt.toString(),
            chatStatusEnum = room.chatStatusEnum!!,
            type = room.type,
            joinUserId = members,
            lastMessage = lastMessage,
            lastMessageTimestamp = lastMessageTimeStamp
        )
    }
}