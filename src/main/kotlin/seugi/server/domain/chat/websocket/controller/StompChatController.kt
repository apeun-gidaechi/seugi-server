package seugi.server.domain.chat.websocket.controller

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import seugi.server.domain.chat.websocket.dto.ChatMessageDto

@Controller
class StompChatController(
    val template: SimpMessagingTemplate
) {
    @MessageMapping("/chat/enter")
    fun enter(message: ChatMessageDto) {
        message.message = message.writer + "입장함"
        template.convertAndSend("/sub/chat/room/" + message.roomId, message)
    }

    @MessageMapping("/chat/message")
    fun message(message: ChatMessageDto){
        template.convertAndSend("/sub/chat/room/" + message.roomId, message)
    }
}