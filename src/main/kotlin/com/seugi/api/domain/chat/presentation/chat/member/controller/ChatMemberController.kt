package com.seugi.api.domain.chat.presentation.chat.member.controller

import com.seugi.api.domain.chat.application.service.chat.member.ChatMemberService
import com.seugi.api.domain.chat.domain.enums.type.EventType
import com.seugi.api.domain.chat.presentation.chat.member.dto.request.ChatMemberEventRequest
import com.seugi.api.global.common.annotation.GetAuthenticatedId
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.*

/**
 * 참가자 추가, 삭제, 확인
 */


@RestController
@RequestMapping("/member")
class ChatMemberController(
    private val chatMemberService: ChatMemberService
) {

    //참가자 추가
    @PostMapping("/add")
    fun addUsers(
        @GetAuthenticatedId userId: Long,
        @RequestBody chatMemberEventRequest: ChatMemberEventRequest
    ): BaseResponse<Unit> {
        return chatMemberService.performCommonEventChangeTasks(
            userId = userId,
            chatMemberEventRequest = chatMemberEventRequest,
            eventType = EventType.ADD
        )
    }

    //참가자 추방
    @PatchMapping("/kick")
    fun kickUsers(
        @GetAuthenticatedId userId: Long,
        @RequestBody chatMemberEventRequest: ChatMemberEventRequest
    ): BaseResponse<Unit> {
        return chatMemberService.performCommonEventChangeTasks(
            userId = userId,
            chatMemberEventRequest = chatMemberEventRequest,
            eventType = EventType.KICK
        )
    }

    // 방장 위임
    @PatchMapping("/toss")
    fun tossAdmin(
        @GetAuthenticatedId userId: Long,
        @RequestBody chatMemberEventRequest: ChatMemberEventRequest
    ): BaseResponse<Unit> {
        return chatMemberService.performCommonEventChangeTasks(
            userId = userId,
            chatMemberEventRequest = chatMemberEventRequest,
            eventType = EventType.TRANSFER_ADMIN
        )
    }

}