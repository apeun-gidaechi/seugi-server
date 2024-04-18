package com.seugi.api.domain.chat.presentation.message.controller

import org.springframework.web.bind.annotation.*
import com.seugi.api.domain.chat.application.service.message.MessageService
import com.seugi.api.domain.chat.domain.chat.embeddable.Emoji
import com.seugi.api.global.common.annotation.GetAuthenticatedId
import com.seugi.api.global.response.BaseResponse

/**
 * 메시지 삭제, 이모지 달기, 읽음표시
 */

@RestController
@RequestMapping("/message")
class MessageController(
    private val messageService: MessageService
) {

    @PostMapping("/emoji/{messageId}")
    fun addEmojiToMessage(
        @PathVariable messageId: String,
        @RequestBody emoji: Emoji,
        @GetAuthenticatedId userId: Long
    ): BaseResponse<Unit> {
        return messageService.addEmojiToMessage(
            messageId = messageId,
            userId = userId,
            emoji = emoji
        )
    }

    @DeleteMapping("/delete/{messageId}")
    fun deleteMessage(
        @PathVariable messageId: String,
        @GetAuthenticatedId userId: Long
    ): BaseResponse<Unit> {
        return messageService.deleteMessage(
            messageId = messageId,
            userId = userId
        )
    }

    @GetMapping("/search/{roomId}")
    fun getMessages(
        @GetAuthenticatedId userId: Long,
        @PathVariable roomId: Long
    ) : BaseResponse<MutableMap<String, Any>> {
        return messageService.getMessages(
            chatRoomId = roomId,
            userId = userId
        )
    }

}