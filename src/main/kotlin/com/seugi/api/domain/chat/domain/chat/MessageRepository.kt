package com.seugi.api.domain.chat.domain.chat

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDateTime

interface MessageRepository : MongoRepository<MessageEntity, ObjectId> {
    fun findByChatRoomId(chatRoomId: String): List<MessageEntity>
    fun findByChatRoomIdEqualsAndTimestampAfter(chatRoomId: String, timestamp: LocalDateTime): List<MessageEntity>
    fun findTop30ByChatRoomIdEqualsAndTimestampBefore(chatRoomId: String, timestamp: LocalDateTime): List<MessageEntity>
}