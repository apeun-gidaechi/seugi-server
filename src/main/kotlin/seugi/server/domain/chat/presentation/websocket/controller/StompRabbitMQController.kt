package seugi.server.domain.chat.presentation.websocket.controller

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpAttributesContextHolder
import org.springframework.stereotype.Controller
import seugi.server.domain.chat.application.service.message.MessageService
import seugi.server.domain.chat.domain.chat.model.Message
import seugi.server.domain.chat.presentation.websocket.dto.ChatMessageDto


@Controller
class StompRabbitMQController(
    private val template: RabbitTemplate,
    private val messageService: MessageService
) {

    @MessageMapping("chat.message")
    fun send(chat: ChatMessageDto) {

        val simpAttributes = SimpAttributesContextHolder.currentAttributes()
        val userId = simpAttributes.getAttribute("user-id") as Long?
        if (userId != null) {
            val domainMessage: Message = messageService.saveMessage(chat, userId)
            template.convertAndSend("chat.exchange", "room.${chat.roomId}", domainMessage)
        }

    }
}