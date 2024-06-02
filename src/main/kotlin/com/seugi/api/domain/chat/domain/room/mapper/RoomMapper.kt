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

@Component
class RoomMapper : Mapper<Room, ChatRoomEntity> {
    override fun toDomain(entity: ChatRoomEntity): Room {
        return Room(
            id = entity.id!!.toString(),
            workspaceID = entity.workspaceID,
            type = entity.roomType,
            roomAdmin = entity.roomAdmin,
            chatName = entity.chatName,
            chatRoomImg = entity.chatRoomImg,
            createdAt = entity.createdAt.toString(),
            joinUserId = entity.joinedUserId,
            chatStatusEnum = entity.chatStatus
        )
    }

    override fun toEntity(domain: Room): ChatRoomEntity {
        return ChatRoomEntity(
            workspaceID = domain.workspaceID,
            chatName = domain.chatName,
            roomAdmin = domain.roomAdmin,
            roomType = domain.type,
            joinedUserId = domain.joinUserId
        )
    }

    fun toRoom(createRoomRequest: CreateRoomRequest, type: RoomType, userId: Long): Room {
        createRoomRequest.joinUsers.add(userId)
        return Room(
            workspaceID = createRoomRequest.workspaceId,
            chatName = createRoomRequest.roomName,
            type = type,
            roomAdmin = if (type == GROUP) userId else -1,
            chatRoomImg = createRoomRequest.chatRoomImg,
            joinUserId = createRoomRequest.joinUsers
        )
    }

    fun toResponse(room: Room, members: Set<RetrieveMemberResponse>): RoomResponse {
        return RoomResponse(
            id = room.id!!,
            workspaceID = room.workspaceID,
            roomAdmin = room.roomAdmin,
            chatName = room.chatName,
            chatRoomImg = room.chatRoomImg,
            createdAt = room.createdAt.toString(),
            chatStatusEnum = room.chatStatusEnum!!,
            type = room.type,
            joinUserId = members
        )
    }
}