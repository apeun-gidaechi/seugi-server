package com.seugi.api.domain.chat.presentation.websocket.controller

import com.seugi.api.domain.chat.application.service.message.MessageService
import com.seugi.api.domain.chat.presentation.websocket.dto.ChatMessageDto
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller


@Controller
class StompRabbitMQController(
    private val messageService: MessageService
) {

    @MessageMapping("chat.message")
    fun send(chat: ChatMessageDto) {
        messageService.message(chat)
    }

}