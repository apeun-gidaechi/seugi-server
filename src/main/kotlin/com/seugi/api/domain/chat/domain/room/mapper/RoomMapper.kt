package com.seugi.api.domain.chat.domain.room.mapper

import org.springframework.stereotype.Component
import com.seugi.api.domain.chat.domain.enums.type.RoomType
import com.seugi.api.domain.chat.domain.room.ChatRoomEntity
import com.seugi.api.domain.chat.domain.room.model.Room
import com.seugi.api.domain.chat.presentation.room.dto.request.CreateRoomRequest
import com.seugi.api.global.common.Mapper

@Component
class RoomMapper: Mapper<Room, ChatRoomEntity> {
    override fun toDomain(entity: ChatRoomEntity): Room {
        return Room(
            id = entity.id!!,
            type = entity.roomType,
            chatName = entity.chatName,
            chatRoomImg = entity.chatRoomImg,
            containUserCnt = entity.containUserCnt,
            createdAt = entity.createdAt.toString(),
            chatStatusEnum = entity.chatStatus,
        )
    }

    override fun toEntity(domain: Room): ChatRoomEntity {
        return ChatRoomEntity(
            chatName = domain.chatName,
            roomType = domain.type,
            containUserCnt = domain.containUserCnt
        )
    }

    fun toRoom(createRoomRequest: CreateRoomRequest, type: RoomType) : Room{
        return Room(
            chatName = createRoomRequest.roomName,
            type = type,
            containUserCnt = createRoomRequest.joinUsers?.size?.toLong()!!,
            chatRoomImg = createRoomRequest.chatRoomImg
        )
    }
}