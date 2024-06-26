package com.seugi.api.domain.chat.presentation.chat.room.controller

import com.seugi.api.domain.chat.application.service.chat.room.ChatRoomService
import com.seugi.api.domain.chat.domain.enums.type.RoomType
import com.seugi.api.domain.chat.presentation.chat.room.dto.request.CreateRoomRequest
import com.seugi.api.domain.chat.presentation.chat.room.dto.request.SearchRoomRequest
import com.seugi.api.domain.chat.presentation.chat.room.dto.response.RoomResponse
import com.seugi.api.global.common.annotation.GetAuthenticatedId
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.*


/**
 * 개인
 * 방 생성, 방 찾기, 방에대한 채팅내역 불러오기
 */

@RestController
@RequestMapping("/chat/personal")
class PersonalChatController(
    private val chatRoomService: ChatRoomService
) {

    @PostMapping("/create")
    fun createRoom(
        @GetAuthenticatedId id: Long,
        @RequestBody createRoomRequest: CreateRoomRequest
    ): BaseResponse<String> {
        return chatRoomService.createChatRoom(createRoomRequest, id, RoomType.PERSONAL)
    }

    @GetMapping("/search")
    fun searchRoom(
        @GetAuthenticatedId userId: Long,
        @RequestParam("workspace", defaultValue = "") workspaceId: String,
        @RequestParam("word", defaultValue = "") word: String
    ): BaseResponse<List<RoomResponse>> {

        return chatRoomService.searchRoomNameIn(
            SearchRoomRequest(workspaceId = workspaceId, word = word),
            RoomType.PERSONAL,
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
            userId = userId,
            type = RoomType.PERSONAL
        )
    }

    @GetMapping("/search/{workspaceId}")
    fun getRooms(
        @GetAuthenticatedId userid: Long,
        @PathVariable workspaceId: String
    ): BaseResponse<List<RoomResponse>> {
        return chatRoomService.getRooms(workspaceId, userid, RoomType.PERSONAL)
    }

}