package seugi.server.domain.chat.presentation.room.controller

import org.springframework.web.bind.annotation.*
import seugi.server.domain.chat.application.service.room.ChatRoomService
import seugi.server.domain.chat.domain.room.model.Room
import seugi.server.domain.chat.presentation.room.dto.request.CreateRoomRequest
import seugi.server.global.common.annotation.GetAuthenticatedId
import seugi.server.global.response.BaseResponse

@RestController
@RequestMapping("/chat/room")
class ChatRoomController(
    private val chatRoomService: ChatRoomService
) {

    @PostMapping("/create")
    fun createRoom(
        @GetAuthenticatedId id: Long,
        @RequestBody createRoomRequest: CreateRoomRequest
    ) : BaseResponse<Unit>{
        return chatRoomService.createChatRoom(createRoomRequest, id)
    }

    @GetMapping("/search")
    fun searchRooms(
        @GetAuthenticatedId userid: Long
    ) : BaseResponse<List<Room>>{
        return chatRoomService.searchRooms(userid)
    }


}