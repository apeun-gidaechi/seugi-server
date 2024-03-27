package seugi.server.domain.chat.application.service.room

import seugi.server.domain.chat.presentation.room.dto.request.CreateRoomRequest


interface ChatRoomService {

    fun createChatRoom(createRoomRequest: CreateRoomRequest)

}