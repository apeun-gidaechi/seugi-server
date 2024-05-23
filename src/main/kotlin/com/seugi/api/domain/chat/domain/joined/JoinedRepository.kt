package com.seugi.api.domain.chat.domain.joined

import com.seugi.api.domain.chat.domain.enums.type.RoomType
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface JoinedRepository : MongoRepository<JoinedEntity, ObjectId> {
    fun findByChatRoomId(chatRoomId: Long): JoinedEntity
    fun findByWorkspaceIDAndJoinedUserIdContainsAndRoomType(
        workspaceID: String,
        userId: Long,
        roomType: RoomType
    ): List<JoinedEntity>
}