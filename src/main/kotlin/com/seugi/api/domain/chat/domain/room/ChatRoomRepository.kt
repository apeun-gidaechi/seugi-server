package com.seugi.api.domain.chat.domain.room

import com.seugi.api.domain.chat.domain.room.model.Room
import org.springframework.data.repository.CrudRepository

interface ChatRoomRepository : CrudRepository<ChatRoomEntity, Long>, ChatRoomRepositoryCustom {
    override fun searchRoom(chatRoomId: Long, input: String): Room?
}
