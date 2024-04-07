package seugi.server.domain.chat.domain.joined

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import seugi.server.domain.chat.domain.enums.type.RoomType

interface JoinedRepository : MongoRepository<JoinedEntity, ObjectId>{
    fun findByChatRoomId(chatRoomId: Long) : JoinedEntity
    fun findByJoinedUserIdEqualsAndRoomType(joinedUserId: Long, roomType: RoomType) : List<JoinedEntity>
}