package com.seugi.api.domain.chat.presentation.websocket.controller

import com.seugi.api.domain.chat.application.service.message.MessageService
import com.seugi.api.domain.chat.presentation.websocket.dto.ChatMessageDto
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpAttributesContextHolder
import org.springframework.stereotype.Controller


@Controller
class StompRabbitMQController(
    private val messageService: MessageService
) {

    @MessageMapping("chat.message")
    fun send(chat: ChatMessageDto) {
        val simpAttributes = SimpAttributesContextHolder.currentAttributes()
        val userId = simpAttributes.getAttribute("user-id") as String?
        messageService.sendAndSaveMessage(chat, userId!!.toLong())
    }

}