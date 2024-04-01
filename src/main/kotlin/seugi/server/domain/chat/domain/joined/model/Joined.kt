package seugi.server.domain.chat.domain.joined.model

data class Joined (
    val chatRoomId : Long,
    val joinUserId : MutableList<Long>,
)