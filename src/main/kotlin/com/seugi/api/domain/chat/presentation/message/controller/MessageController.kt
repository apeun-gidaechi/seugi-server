package com.seugi.api.domain.chat.presentation.message.controller

import com.seugi.api.domain.chat.application.service.message.MessageService
import com.seugi.api.domain.chat.domain.chat.embeddable.AddEmoji
import com.seugi.api.domain.chat.domain.chat.embeddable.DeleteMessage
import com.seugi.api.domain.chat.presentation.chat.member.dto.response.GetMessageResponse
import com.seugi.api.domain.member.domain.model.Member
import com.seugi.api.global.common.annotation.GetResolvedMember
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

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
        @GetResolvedMember model: Member
    ): BaseResponse<Unit> {
        return messageService.addEmojiToMessage(
            userId = model.id,
            emoji = emoji
        )
    }

    @DeleteMapping("/emoji")
    fun deleteEmojiToMessage(
        @RequestBody emoji: AddEmoji,
        @GetResolvedMember model: Member
    ): BaseResponse<Unit> {
        return messageService.deleteEmojiToMessage(
            userId = model.id,
            emoji = emoji
        )
    }

    @DeleteMapping("/delete")
    fun deleteMessage(
        @RequestBody deleteMessage: DeleteMessage,
        @GetResolvedMember model: Member
    ): BaseResponse<Unit> {
        return messageService.deleteMessage(
            deleteMessage = deleteMessage,
            userId = model.id
        )
    }

    @GetMapping("/search/{roomId}")
    fun getMessages(
        @GetResolvedMember model: Member,
        @PathVariable roomId: String,
        @RequestParam("timestamp", required = false) timestamp: LocalDateTime = LocalDateTime.now(),
    ): BaseResponse<GetMessageResponse> {
        return messageService.getMessages(
            chatRoomId = roomId,
            userId = model.id,
            timestamp = timestamp
        )
    }

}