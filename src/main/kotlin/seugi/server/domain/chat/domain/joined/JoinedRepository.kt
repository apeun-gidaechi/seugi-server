package seugi.server.domain.chat.domain.joined

import org.springframework.data.mongodb.repository.MongoRepository

interface JoinedRepository : MongoRepository<JoinedEntity, Long>{
    fun findByChatRoomId(chatRoomId: Long) : JoinedEntity
    fun findByJoinedUserIdEquals(joinedUserId: Long) : List<JoinedEntity>
}