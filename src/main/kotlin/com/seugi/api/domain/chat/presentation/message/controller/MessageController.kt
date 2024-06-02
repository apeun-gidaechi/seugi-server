package com.seugi.api.domain.chat.presentation.message.controller

import com.seugi.api.domain.chat.application.service.message.MessageService
import com.seugi.api.domain.chat.domain.chat.embeddable.AddEmoji
import com.seugi.api.domain.chat.domain.chat.embeddable.DeleteMessage
import com.seugi.api.domain.chat.presentation.chat.member.dto.response.GetMessageResponse
import com.seugi.api.global.common.annotation.GetAuthenticatedId
import com.seugi.api.global.response.BaseResponse
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

/**
 * 메시지 삭제, 이모지 달기, 읽음표시
 */

@RestController
@RequestMapping("/message")
class MessageController(
    private val messageService: MessageService
) {

    @PutMapping("/emoji")
    fun addEmojiToMessage(
        @RequestBody emoji: AddEmoji,
        @GetAuthenticatedId userId: Long
    ): BaseResponse<Unit> {
        return messageService.addEmojiToMessage(
            userId = userId,
            emoji = emoji
        )
    }

    @DeleteMapping("/emoji")
    fun deleteEmojiToMessage(
        @RequestBody emoji: AddEmoji,
        @GetAuthenticatedId userId: Long
    ): BaseResponse<Unit> {
        return messageService.addEmojiToMessage(
            userId = userId,
            emoji = emoji
        )
    }

    @DeleteMapping("/delete")
    fun deleteMessage(
        @RequestBody deleteMessage: DeleteMessage,
        @GetAuthenticatedId userId: Long
    ): BaseResponse<Unit> {
        return messageService.deleteMessage(
            deleteMessage = deleteMessage,
            userId = userId
        )
    }

    @GetMapping("/search/{roomId}")
    fun getMessages(
        @GetAuthenticatedId userId: Long,
        @PathVariable roomId: Long,
        @PageableDefault(sort = ["id"], direction = Sort.Direction.DESC, size = 20) pageable: Pageable
    ): BaseResponse<GetMessageResponse> {
        return messageService.getMessages(
            chatRoomId = roomId,
            userId = userId,
            pageable = pageable
        )
    }

}