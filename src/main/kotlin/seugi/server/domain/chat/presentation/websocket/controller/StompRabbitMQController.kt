package seugi.server.domain.chat.presentation.websocket.controller

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import seugi.server.domain.chat.presentation.websocket.dto.ChatMessageDto


@Controller
class StompRabbitMQController(
    private val template: RabbitTemplate
) {

    @MessageMapping("chat.message")
    fun send(chat: ChatMessageDto) {
        template.convertAndSend("chat.exchange", "room.${chat.roomId}", chat)
    }
}