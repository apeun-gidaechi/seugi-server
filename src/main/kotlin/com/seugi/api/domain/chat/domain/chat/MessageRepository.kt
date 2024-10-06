package com.seugi.api.domain.chat.domain.chat

import org.bson.types.ObjectId
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDateTime

interface MessageRepository : MongoRepository<MessageEntity, ObjectId> {
    fun findByChatRoomId(chatRoomId: String): List<MessageEntity>
    fun findByChatRoomIdEquals(chatRoomId: String, pageable: Pageable): List<MessageEntity>
    fun findByChatRoomIdEqualsAndTimestampBefore(chatRoomId: String, timestamp: LocalDateTime): List<MessageEntity>
}