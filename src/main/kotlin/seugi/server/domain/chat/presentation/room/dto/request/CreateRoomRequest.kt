package seugi.server.domain.chat.presentation.room.dto.request

data class CreateRoomRequest(
    val roomName : String,
    val joinUsers : List<Long>
)