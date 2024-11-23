package com.seugi.api.domain.chat.presentation.websocket.handler

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.seugi.api.global.response.ErrorResponse
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import org.springframework.messaging.MessageDeliveryException
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.MessageBuilder
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler
import java.nio.charset.StandardCharsets
import java.security.SignatureException

@Configuration
class StompErrorHandler(private val objectMapper: ObjectMapper) : StompSubProtocolErrorHandler() {

    override fun handleClientMessageProcessingError(clientMessage: Message<ByteArray>?, ex: Throwable): Message<ByteArray>? {

        return when (ex) {
            is MessageDeliveryException -> {
                when (val cause = ex.cause) {
                    is AccessDeniedException -> {
                        sendErrorMessage(ErrorResponse(status = 4403, message = "Access denied"))
                    }
                    else -> {
                        if (isJwtException(cause)) {
                            sendErrorMessage(ErrorResponse(status = 4403, message = cause?.message ?: "JWT Exception"))
                        } else {
                            sendErrorMessage(ErrorResponse(status = 4403, message = cause?.stackTraceToString() ?: "Unhandled exception"))
                        }
                    }
                }
            }
            else -> {
                sendErrorMessage(ErrorResponse(status = 4400, message = ex.message ?: "Unhandled root exception"))
            }
        }
    }

    private fun isJwtException(ex: Throwable?): Boolean {
        return ex is SignatureException || ex is ExpiredJwtException || ex is MalformedJwtException || ex is UnsupportedJwtException || ex is IllegalArgumentException
    }

    private fun sendErrorMessage(errorResponse: ErrorResponse): Message<ByteArray> {
        val headers = StompHeaderAccessor.create(StompCommand.ERROR).apply {
            message = errorResponse.message
        }
        return try {
            val json = objectMapper.writeValueAsString(errorResponse)
            MessageBuilder.createMessage(json.toByteArray(StandardCharsets.UTF_8), headers.messageHeaders)
        } catch (e: JsonProcessingException) {
            MessageBuilder.createMessage(errorResponse.message.toByteArray(StandardCharsets.UTF_8), headers.messageHeaders)
        }
    }
}