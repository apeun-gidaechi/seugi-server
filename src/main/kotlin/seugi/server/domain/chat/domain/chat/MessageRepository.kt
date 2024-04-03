package seugi.server.domain.chat.domain.chat

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface MessageRepository :MongoRepository<MessageEntity, ObjectId>{
    fun findByChatRoomIdEquals(chatRoomId: Long) : List<MessageEntity>
    fun findByChatRoomIdEqualsAndAuthorId(chatRoomId: Long, authorId: Long): List<MessageEntity>
}