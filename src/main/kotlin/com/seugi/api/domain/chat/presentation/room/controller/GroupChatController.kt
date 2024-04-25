package com.seugi.api.domain.chat.presentation.room.controller

import org.springframework.web.bind.annotation.*
import com.seugi.api.domain.chat.application.service.room.ChatRoomService
import com.seugi.api.domain.chat.domain.enums.type.RoomType
import com.seugi.api.domain.chat.domain.room.model.Room
import com.seugi.api.domain.chat.presentation.room.dto.request.CreateRoomRequest
import com.seugi.api.domain.chat.presentation.room.dto.request.SearchRoomRequest
import com.seugi.api.global.common.annotation.GetAuthenticatedId
import com.seugi.api.global.response.BaseResponse

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
    ) : BaseResponse<Long> {
        return chatRoomService.createChatRoom(createRoomRequest, id, RoomType.GROUP)
    }

    @GetMapping("/search")
    fun searchRoom(
        @GetAuthenticatedId userId: Long,
        @RequestBody searchRoomRequest: SearchRoomRequest
    ): BaseResponse<List<Room>> {
        return chatRoomService.searchRoomNameIn(searchRoomRequest, RoomType.GROUP, userId)
    }

    @GetMapping("/search/{workspaceID}")
    fun getRooms(
        @GetAuthenticatedId userid: Long,
        @PathVariable workspaceID: String,
    ) : BaseResponse<List<Room>> {
        return chatRoomService.getRooms(workspaceID, userid, RoomType.GROUP)
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