package com.seugi.api.domain.chat.domain.room

import com.querydsl.jpa.impl.JPAQueryFactory
import com.seugi.api.domain.chat.domain.room.mapper.RoomMapper
import com.seugi.api.domain.chat.domain.room.model.Room

class ChatRoomRepositoryCustomImpl(
    private val jpaQueryFactory: JPAQueryFactory,
    private val chatRoomMapper: RoomMapper
) : ChatRoomRepositoryCustom {

    private val chatRoomEntity: QChatRoomEntity = QChatRoomEntity.chatRoomEntity

    override fun searchRoom(input: String): List<Room> {
        val rooms = jpaQueryFactory
            .selectFrom(chatRoomEntity)
            .where(chatRoomEntity.chatName.contains(input))
            .fetch()

        return rooms.map { chatRoomMapper.toDomain(it) }
    }

}