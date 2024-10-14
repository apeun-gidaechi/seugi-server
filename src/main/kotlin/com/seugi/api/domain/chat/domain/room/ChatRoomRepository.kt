package com.seugi.api.domain.chat.domain.room

import com.seugi.api.domain.chat.domain.enums.status.ChatStatusEnum
import com.seugi.api.domain.chat.domain.enums.type.RoomType
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository

interface ChatRoomRepository : MongoRepository<ChatRoomEntity, ObjectId> {
    fun findByWorkspaceIdAndChatStatusAndRoomTypeAndJoinedUserInfoUserId(
        workspaceId: String,
        chatStatus: ChatStatusEnum,
        roomType: RoomType,
        userId: Long,
    ): MutableList<ChatRoomEntity>?

    @Aggregation(
        pipeline = [
            "{ '\$match': { 'workspaceId': ?0, 'roomType': ?1 } }",
            "{ '\$project': { 'joinedUserIds': '\$joinedUserInfo.userId', 'equalSize': { \$eq: [{ \$size: '\$joinedUserInfo'}, ?2] }, 'workspaceId': 1, 'roomType': 1, 'roomAdmin': 1, 'chatName': 1, 'chatRoomImg': 1, 'createdAt': 1, 'joinedUserInfo': 1, 'chatStatus': 1 } }",
            "{ '\$match': { 'joinedUserIds': { '\$all': ?3 }, 'equalSize': true } }",
            "{ '\$limit': 1 }"
        ]
    )
    fun findByWorkspaceIdAndRoomTypeAndExactJoinedUserIds(
        workspaceId: String,
        roomType: RoomType,
        userCount: Int,
        joinedUserIds: Set<Long>,
    ): ChatRoomEntity?
}