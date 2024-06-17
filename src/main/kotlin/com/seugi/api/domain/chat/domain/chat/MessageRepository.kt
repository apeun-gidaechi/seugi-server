package com.seugi.api.domain.chat.domain.chat

import org.bson.types.ObjectId
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface MessageRepository : MongoRepository<MessageEntity, ObjectId> {
    fun findByChatRoomId(chatRoomId: String): List<MessageEntity>
    fun findByChatRoomIdAndRead(chatRoomId: String, read: Set<Long>): List<MessageEntity>
    fun findByChatRoomIdEquals(chatRoomId: String, pageable: Pageable): List<MessageEntity>
    fun findByChatRoomIdEqualsAndReadNot(chatRoomId: String, read: Set<Long>): List<MessageEntity>
}