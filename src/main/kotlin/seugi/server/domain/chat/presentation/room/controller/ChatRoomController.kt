package seugi.server.domain.chat.presentation.room.controller

import org.springframework.web.bind.annotation.*
import seugi.server.domain.chat.application.service.message.MessageService
import seugi.server.domain.chat.application.service.room.ChatRoomService
import seugi.server.domain.chat.domain.chat.model.Message
import seugi.server.domain.chat.domain.room.model.Room
import seugi.server.domain.chat.presentation.room.dto.request.CreateRoomRequest
import seugi.server.global.common.annotation.GetAuthenticatedId
import seugi.server.global.response.BaseResponse

/**
 * 방 생성 or 삭제 or 수정(이름, 사진), 방 찾기, 방에대한 채팅내역 불러오기
 */

@RestController
@RequestMapping("/chat/room")
class ChatRoomController(
    private val chatRoomService: ChatRoomService,
    private val messageService: MessageService
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

    @GetMapping("/search/{roomId}")
    fun getMessages(
        @GetAuthenticatedId userId: Long,
        @PathVariable roomId: Long
    ) : BaseResponse<List<Message>>{
        return messageService.getMessages(
            chatRoomId = roomId,
            userId = userId
        )
    }

}