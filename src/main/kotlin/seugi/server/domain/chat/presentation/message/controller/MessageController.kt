package seugi.server.domain.chat.presentation.message.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import seugi.server.domain.chat.application.service.message.MessageService
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
        @PathVariable messageId: Long,
        @GetAuthenticatedId userId: Long
    ): BaseResponse<Unit> {
        return messageService.addEmojiToMessage(
            messageId = messageId,
            userId = userId
        )
    }

    @DeleteMapping("/delete/{messageId}")
    fun deleteMessage(
        @PathVariable messageId: Long,
        @GetAuthenticatedId userId: Long
    ): BaseResponse<Unit> {
        return messageService.deleteMessage(
            messageId = messageId,
            userId = userId
        )
    }


}