package com.seugi.api.global.exception

import com.seugi.api.global.response.ErrorResponse
import org.springframework.messaging.handler.annotation.MessageExceptionHandler
import org.springframework.messaging.Message
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.web.bind.annotation.ControllerAdvice
import java.io.IOException
import java.net.SocketException
import java.security.Principal

@ControllerAdvice
class CustomSocketExceptionHandler(
    private val template: SimpMessagingTemplate
) {

    private val bindingUrl = "/queue/errors"

    @MessageExceptionHandler(SocketException::class)
    fun handleSocketException(message: Message<*>, principal: Principal, ex: Exception) {
        removeSession(message)
        template.convertAndSendToUser(
            principal.name,
            bindingUrl,
            ErrorResponse(status = 4500, message = ex.cause?.message ?: "Socket Error")
        )
    }

    @MessageExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(principal: Principal, ex: Exception) {
        template.convertAndSendToUser(
            principal.name,
            bindingUrl,
            ErrorResponse(status = 4500, message = ex.cause?.stackTraceToString() ?: "Socket Error")
        )
    }

    @Throws(IOException::class)
    private fun removeSession(message: Message<*>) {
        val stompHeaderAccessor = StompHeaderAccessor.wrap(message)
        val sessionId = stompHeaderAccessor.sessionId
        stompHeaderAccessor.sessionAttributes?.remove(sessionId)
    }


}