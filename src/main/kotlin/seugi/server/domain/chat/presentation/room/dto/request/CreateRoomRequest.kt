package seugi.server.domain.chat.presentation.room.dto.request

data class CreateRoomRequest(
    val roomName : String = "채팅방",
    val joinUsers : List<Long>? = emptyList()
)