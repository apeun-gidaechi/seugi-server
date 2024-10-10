package com.seugi.api.domain.chat.domain.room

import com.seugi.api.domain.chat.domain.enums.status.ChatStatusEnum
import com.seugi.api.domain.chat.domain.enums.type.RoomType
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface ChatRoomRepository : MongoRepository<ChatRoomEntity, ObjectId> {
    fun findByWorkspaceIdAndChatStatusAndRoomTypeAndJoinedUserInfoUserId(
        workspaceId: String,
        chatStatus: ChatStatusEnum,
        roomType: RoomType,
        userId: Long,
    ): MutableList<ChatRoomEntity>?

    fun findByWorkspaceIdAndJoinedUserInfoUserIdInAndRoomType(
        workspaceId: String,
        joinedUserId: MutableSet<Long>,
        roomType: RoomType,
    ): ChatRoomEntity?
}