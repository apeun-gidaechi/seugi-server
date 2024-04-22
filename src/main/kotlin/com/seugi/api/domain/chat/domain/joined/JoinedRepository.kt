package com.seugi.api.domain.chat.domain.joined

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import com.seugi.api.domain.chat.domain.enums.type.RoomType

interface JoinedRepository : MongoRepository<JoinedEntity, ObjectId>{
    fun findByChatRoomId(chatRoomId: Long) : JoinedEntity
    fun findByWorkspaceIDAndJoinedUserIdContainsAndRoomType(workspaceID: String, userId: Long, roomType:RoomType) : List<JoinedEntity>
}