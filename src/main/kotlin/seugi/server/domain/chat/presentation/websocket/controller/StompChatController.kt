package seugi.server.domain.chat.presentation.websocket.controller

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpAttributesContextHolder
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
        message: ChatMessageDto
    ) {
        val simpAttributes = SimpAttributesContextHolder.currentAttributes()
        val userId = simpAttributes.getAttribute("user-id") as String?
        val userName = simpAttributes.getAttribute("user-name") as String?

        message.userId = userId?.toLong()
        message.writer = userName

        template.convertAndSend("/sub/chat/room/" + message.roomId, message)

        if (userId != null) {
            messageService.saveMessage(message)
        }
    }
}