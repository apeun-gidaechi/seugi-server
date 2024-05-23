package com.seugi.api.domain.chat.presentation.room.controller

import com.seugi.api.domain.chat.application.service.room.ChatRoomService
import com.seugi.api.domain.chat.domain.enums.type.RoomType
import com.seugi.api.domain.chat.presentation.room.dto.request.CreateRoomRequest
import com.seugi.api.domain.chat.presentation.room.dto.request.SearchRoomRequest
import com.seugi.api.domain.chat.presentation.room.dto.response.RoomResponse
import com.seugi.api.global.common.annotation.GetAuthenticatedId
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.*

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
    ): BaseResponse<String> {
        return chatRoomService.createChatRoom(createRoomRequest, id, RoomType.GROUP)
    }

    @GetMapping("/search")
    fun searchRoom(
        @GetAuthenticatedId userId: Long,
        @RequestParam("workspace", defaultValue = "") workspaceId: String,
        @RequestParam("word", defaultValue = "") word: String
    ): BaseResponse<List<RoomResponse>> {
        return chatRoomService.searchRoomNameIn(
            SearchRoomRequest(workspaceId = workspaceId, word = word),
            RoomType.GROUP,
            userId
        )
    }

    @GetMapping("/search/room/{roomId}")
    fun getRoom(
        @PathVariable("roomId") roomId: String,
        @GetAuthenticatedId userId: Long
    ): BaseResponse<RoomResponse> {
        return chatRoomService.getRoom(
            roomId = roomId,
            userId = userId
        )
    }

//    @GetMapping("/search/{workspaceID}")
//    fun getRooms(
//        @GetAuthenticatedId userid: Long,
//        @PathVariable workspaceID: String,
//    ): BaseResponse<List<RoomResponse>> {
//        return chatRoomService.getRooms(workspaceID, userid, RoomType.GROUP)
//    }

//
//    //나가기
//    @PatchMapping("/left/{roomId}")
//    fun leftRoom(
//        @GetAuthenticatedId userId: Long,
//        @PathVariable roomId: Long,
//    ): BaseResponse<Unit> {
//        return chatRoomService.leftRoom(userId, roomId)
//    }
//
}