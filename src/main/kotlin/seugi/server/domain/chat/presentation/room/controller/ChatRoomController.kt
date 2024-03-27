package seugi.server.domain.chat.presentation.room.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import seugi.server.domain.chat.presentation.room.dto.request.CreateRoomRequest

@RestController
@RequestMapping("/chat/room")
class ChatRoomController() {

    @PostMapping("/create")
    fun createRoom(createRoomRequest: CreateRoomRequest){

    }
}