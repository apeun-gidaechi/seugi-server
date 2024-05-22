package com.seugi.api.domain.chat.domain.room.info

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash("RoomInfo")
class RoomInfoEntity(
    @Id
    val userId: Long,
    @Indexed
    val roomId: String
)