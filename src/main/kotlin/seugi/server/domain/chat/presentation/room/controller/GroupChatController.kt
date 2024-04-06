package seugi.server.domain.chat.presentation.room.controller

import org.springframework.web.bind.annotation.*
import seugi.server.domain.chat.application.service.room.ChatRoomService
import seugi.server.domain.chat.domain.enums.type.RoomType
import seugi.server.domain.chat.domain.room.model.Room
import seugi.server.domain.chat.presentation.room.dto.request.CreateRoomRequest
import seugi.server.global.common.annotation.GetAuthenticatedId
import seugi.server.global.response.BaseResponse

/**
 * 그룹
 * 방 생성 or 삭제 or 수정(이름, 사진), 방 찾기, 방에대한 채팅내역 불러오기
 */

@RestController
@RequestMapping("/chat/group")
class GroupChatController(
    private val chatRoomService: ChatRoomService,
) {

    @PostMapping("/create")
    fun createRoom(
        @GetAuthenticatedId id: Long,
        @RequestBody createRoomRequest: CreateRoomRequest
    ) : BaseResponse<Long>{
        return chatRoomService.createChatRoom(createRoomRequest, id, RoomType.GROUP)
    }

    @GetMapping("/search")
    fun getRooms(
        @GetAuthenticatedId userid: Long
    ) : BaseResponse<List<Room>>{
        return chatRoomService.getRooms(userid, RoomType.GROUP)
    }


    //나가기
    @PatchMapping("/left/{roomId}")
    fun leftRoom(
        @GetAuthenticatedId userId:Long,
        @PathVariable roomId: Long,
    ): BaseResponse<Unit> {
        return chatRoomService.leftRoom(userId,roomId)
    }

}