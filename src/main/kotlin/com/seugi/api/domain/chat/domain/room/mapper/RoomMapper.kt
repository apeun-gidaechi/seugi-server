package com.seugi.api.domain.chat.domain.room.mapper

import com.seugi.api.domain.chat.domain.enums.type.RoomType
import com.seugi.api.domain.chat.domain.enums.type.RoomType.GROUP
import com.seugi.api.domain.chat.domain.room.ChatRoomEntity
import com.seugi.api.domain.chat.domain.room.model.JoinUserInfo
import com.seugi.api.domain.chat.domain.room.model.Room
import com.seugi.api.domain.chat.presentation.chat.room.dto.request.CreateRoomRequest
import com.seugi.api.domain.chat.presentation.chat.room.dto.response.RoomResponse
import com.seugi.api.domain.chat.presentation.chat.room.dto.response.UserInfoResponse
import com.seugi.api.domain.member.presentation.controller.dto.res.RetrieveMemberResponse
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
            joinUserInfo = entity.joinedUserInfo,
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
            joinedUserInfo = domain.joinUserInfo
        )
    }

    private fun toJoinUserInfo(userId: Long): JoinUserInfo {
        return JoinUserInfo(
            userId = userId,
            timestamp = LocalDateTime.now()
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
            joinUserInfo = createRoomRequest.joinUsers.map {
                toJoinUserInfo(it)
            }.toSet()
        )
    }

    fun toResponse(
        room: Room,
        members: Set<RetrieveMemberResponse>,
        lastMessage: String,
        lastMessageTimeStamp: String,
        notReadCnt: Int
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
            joinUserInfo = toJoinUserInfo(members, room),
            lastMessage = lastMessage,
            lastMessageTimestamp = lastMessageTimeStamp,
            notReadCnt = notReadCnt
        )
    }

    private fun toJoinUserInfo(members: Set<RetrieveMemberResponse>, room: Room): Set<UserInfoResponse> {

        return members.map { member ->
            UserInfoResponse(
                userInfo = member,
                timestamp = room.joinUserInfo.find { it.userId == member.id }?.timestamp.toString()
            )
        }.toSet()

    }
}