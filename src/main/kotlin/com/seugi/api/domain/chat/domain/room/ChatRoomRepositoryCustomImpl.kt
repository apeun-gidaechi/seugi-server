package com.seugi.api.domain.chat.domain.room

import com.querydsl.jpa.impl.JPAQueryFactory
import com.seugi.api.domain.chat.domain.room.mapper.RoomMapper
import com.seugi.api.domain.chat.domain.room.model.Room

class ChatRoomRepositoryCustomImpl(
    private val jpaQueryFactory: JPAQueryFactory,
    private val chatRoomMapper: RoomMapper
) : ChatRoomRepositoryCustom {

    private val chatRoomEntity: QChatRoomEntity = QChatRoomEntity.chatRoomEntity

    override fun searchRoom(chatRoomId: Long, input: String): Room? {
        val room = jpaQueryFactory
            .selectFrom(chatRoomEntity)
            .where(chatRoomEntity.id.eq(chatRoomId), chatRoomEntity.chatName.contains(input))
            .fetchFirst()

        return room?.let { chatRoomMapper.toDomain(it) }
    }

}