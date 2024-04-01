package seugi.server.domain.chat.presentation.websocket.controller

import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import seugi.server.domain.chat.application.service.message.MessageService
import seugi.server.domain.chat.presentation.websocket.dto.ChatMessageDto

@Controller
class StompChatController(
    val template: SimpMessagingTemplate,
    private val messageService: MessageService
) {
    @MessageMapping("/chat/message")
    fun message(
        message: ChatMessageDto,
        @Header("user-id") userId: String
    ) {
        template.convertAndSend("/sub/chat/room/" + message.roomId, message)
        println("++++++++++$userId+++++++++++")
        messageService.saveMessage(message, userId.toLong())
    }
}