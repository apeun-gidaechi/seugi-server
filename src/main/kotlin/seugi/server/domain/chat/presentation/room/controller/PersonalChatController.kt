package seugi.server.domain.chat.presentation.room.controller

import org.springframework.web.bind.annotation.*
import seugi.server.domain.chat.application.service.room.ChatRoomService
import seugi.server.domain.chat.domain.enums.type.RoomType
import seugi.server.domain.chat.domain.room.model.Room
import seugi.server.domain.chat.presentation.room.dto.request.CreateRoomRequest
import seugi.server.global.common.annotation.GetAuthenticatedId
import seugi.server.global.response.BaseResponse


/**
 * 개인
 * 방 생성, 방 찾기, 방에대한 채팅내역 불러오기
 */

@RestController
@RequestMapping("/chat/personal")
class PersonalChatController(
    private val chatRoomService: ChatRoomService,
) {

    @PostMapping("/create")
    fun createRoom(
        @GetAuthenticatedId id: Long,
        @RequestBody createRoomRequest: CreateRoomRequest
    ) : BaseResponse<Long> {
        return chatRoomService.createChatRoom(createRoomRequest, id, RoomType.PERSONAL)
    }

    @GetMapping("/search")
    fun getRooms(
        @GetAuthenticatedId userid: Long
    ) : BaseResponse<List<Room>> {
        return chatRoomService.getRooms(userid, RoomType.PERSONAL)
    }

}