package seugi.server.domain.chat.presentation.room.dto.request

import seugi.server.domain.chat.domain.joined.model.Joined

data class CreateRoomRequest(
    val roomName : String,
    val joinUsers : List<Joined>
)