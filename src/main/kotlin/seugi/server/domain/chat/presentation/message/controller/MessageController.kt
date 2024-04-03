package seugi.server.domain.chat.presentation.message.controller

import org.springframework.web.bind.annotation.*
import seugi.server.domain.chat.application.service.message.MessageService
import seugi.server.domain.chat.domain.chat.embeddable.Emoji
import seugi.server.global.common.annotation.GetAuthenticatedId
import seugi.server.global.response.BaseResponse

/**
 * 메시지 삭제, 이모지 달기, 읽음표시
 */

@RestController
@RequestMapping("/message")
class MessageController(
    private val messageService: MessageService
) {
    @PostMapping("/{chatRoomId}")
    fun readMessage(
        @PathVariable chatRoomId: Long,
        @GetAuthenticatedId userId: Long
    ): BaseResponse<Unit> {
        return messageService.readMessage(
            chatRoomId = chatRoomId,
            userId = userId
        )
    }

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


}