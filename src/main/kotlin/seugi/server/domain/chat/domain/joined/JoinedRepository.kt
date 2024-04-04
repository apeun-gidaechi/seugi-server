package seugi.server.domain.chat.domain.joined

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface JoinedRepository : MongoRepository<JoinedEntity, ObjectId>{
    fun findByChatRoomId(chatRoomId: Long) : JoinedEntity
    fun findByJoinedUserIdEquals(joinedUserId: Long) : List<JoinedEntity>
}