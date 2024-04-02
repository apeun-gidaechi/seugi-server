package seugi.server.domain.chat.domain.chat

import org.springframework.data.mongodb.repository.MongoRepository

interface MessageRepository :MongoRepository<MessageEntity, Long>{
    fun findByChatRoomIdEquals(chatRoomId: Long) : List<MessageEntity>
}