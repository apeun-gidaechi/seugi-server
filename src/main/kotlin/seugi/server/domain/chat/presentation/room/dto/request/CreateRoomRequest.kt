package seugi.server.domain.chat.presentation.room.dto.request

data class CreateRoomRequest(
    var roomName : String = "",
    val joinUsers : MutableList<Long>? = emptyArray<Long>().toMutableList()
)