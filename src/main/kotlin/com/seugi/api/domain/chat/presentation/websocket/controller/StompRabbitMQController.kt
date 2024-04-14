package com.seugi.api.domain.chat.presentation.websocket.controller

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpAttributesContextHolder
import org.springframework.stereotype.Controller
import com.seugi.api.domain.chat.application.service.message.MessageService
import com.seugi.api.domain.chat.domain.chat.model.Message
import com.seugi.api.domain.chat.presentation.websocket.dto.ChatMessageDto


@Controller
class StompRabbitMQController(
    private val template: RabbitTemplate,
    private val messageService: MessageService
) {

    @MessageMapping("chat.message")
    fun send(chat: ChatMessageDto) {

        val simpAttributes = SimpAttributesContextHolder.currentAttributes()
        val userId = simpAttributes.getAttribute("user-id") as String?
        if (userId != null) {
            val domainMessage: Message = messageService.saveMessage(chat, userId.toLong())
            template.convertAndSend("chat.exchange", "room.${chat.roomId}", domainMessage)
        }

    }
}