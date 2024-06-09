package com.seugi.api.domain.chat.domain.chat

import org.bson.types.ObjectId
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface MessageRepository : MongoRepository<MessageEntity, ObjectId> {
    fun findByChatRoomIdEquals(chatRoomId: ObjectId, pageable: Pageable): List<MessageEntity>
    fun findByChatRoomIdEqualsAndReadNot(chatRoomId: ObjectId, read: Set<Long>): List<MessageEntity>
}