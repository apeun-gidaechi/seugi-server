package com.seugi.api.domain.chat.presentation.chat.room.controller

import com.seugi.api.domain.chat.application.service.chat.room.ChatRoomService
import com.seugi.api.domain.chat.domain.enums.type.RoomType
import com.seugi.api.domain.chat.presentation.chat.room.dto.request.CreateRoomRequest
import com.seugi.api.domain.chat.presentation.chat.room.dto.request.SearchRoomRequest
import com.seugi.api.domain.chat.presentation.chat.room.dto.response.RoomResponse
import com.seugi.api.domain.member.domain.MemberRepository
import com.seugi.api.domain.member.domain.model.Member
import com.seugi.api.global.common.annotation.GetResolvedMember
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
    private val memberRepository: MemberRepository,
) {

    @PostMapping("/create")
    fun createRoom(
        @GetResolvedMember model: Member,
        @RequestBody createRoomRequest: CreateRoomRequest
    ): BaseResponse<String> {
        return chatRoomService.createChatRoom(createRoomRequest, model.id, RoomType.GROUP)
    }

    @GetMapping("/search")
    fun searchRoom(
        @GetResolvedMember model: Member,
        @RequestParam("workspace", defaultValue = "") workspaceId: String,
        @RequestParam("word", defaultValue = "") word: String
    ): BaseResponse<List<RoomResponse>> {
        return chatRoomService.searchRoomNameIn(
            SearchRoomRequest(workspaceId = workspaceId, word = word),
            RoomType.GROUP,
            model.id
        )
    }

    @GetMapping("/search/room/{roomId}")
    fun getRoom(
        @PathVariable("roomId") roomId: String,
        @GetResolvedMember model: Member
    ): BaseResponse<RoomResponse> {
        return chatRoomService.getRoom(
            roomId = roomId,
            userId = model.id,
            type = RoomType.GROUP
        )
    }

    @GetMapping("/search/{workspaceID}")
    fun getRooms(
        @GetResolvedMember model: Member,
        @PathVariable workspaceID: String,
    ): BaseResponse<List<RoomResponse>> {
        return chatRoomService.getRooms(workspaceID, model.id, RoomType.GROUP)
    }


    //나가기
    @PatchMapping("/left/{roomId}")
    fun leftRoom(
        @GetResolvedMember model: Member,
        @PathVariable roomId: String,
    ): BaseResponse<Unit> {
        return chatRoomService.leftRoom(model.id, roomId)
    }

}