package com.seugi.api.domain.chat.domain.room.info

import org.springframework.data.repository.CrudRepository

interface RoomInfoRepository : CrudRepository<RoomInfoEntity, Long> {
    fun findByRoomId(roomId: String): List<RoomInfoEntity>?
}