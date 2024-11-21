package com.seugi.api.global.exception

import com.seugi.api.global.response.ErrorResponse
import org.springframework.messaging.handler.annotation.MessageExceptionHandler
import org.springframework.messaging.simp.SimpAttributesContextHolder
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.ControllerAdvice
import java.net.SocketException
import java.security.Principal

@ControllerAdvice
class CustomSocketExceptionHandler(
//    private val template: RabbitMessagingTemplate
    private val template: SimpMessagingTemplate,
) {

    @MessageExceptionHandler(SocketException::class)
    fun handleSocketException(principal: Principal, ex: Exception) {
        template.convertAndSendToUser(
            principal.name,
            "/error",
            ErrorResponse(status = 4500, message = ex.cause?.message ?: "Socket Error")
        )
    }

    @MessageExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(principal: Principal, ex: Exception) {
        val simpAttributes = SimpAttributesContextHolder.currentAttributes()
        println(
            principal.name + " | " + simpAttributes.getAttribute("sub").toString() + " | " + simpAttributes.sessionId
        )
        template.convertAndSendToUser(
            principal.name,
            "/queue/errors",
            ErrorResponse(status = 4500, message = ex.cause?.message ?: "Socket Error")
        )
    }

//    @Throws(IOException::class)
//    private fun removeSession(message: Message<*>) {
//        val stompHeaderAccessor = StompHeaderAccessor.wrap(message)
//        val sessionId = stompHeaderAccessor.sessionId
//    }


}