package seugi.server.domain.chat.domain.joined.model

import seugi.server.domain.chat.domain.enums.type.RoomType

data class Joined (
    val chatRoomId : Long,
    val roomType: RoomType,
    val roomAdmin: Long,
    val joinUserId : MutableSet<Long>,
)