package seugi.server.domain.chat.presentation.websocket.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageBuilder
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import seugi.server.global.auth.jwt.JwtUserDetails
import seugi.server.global.auth.jwt.JwtUtils

@Configuration
@EnableWebSocketMessageBroker
class StompWebSocketConfig(
    private val jwtUtils: JwtUtils
) : WebSocketMessageBrokerConfigurer {

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/stomp/chat")
            .setAllowedOrigins("*")
    }

    override fun configureMessageBroker(config: MessageBrokerRegistry) {
        config.enableSimpleBroker("/sub")
        config.setApplicationDestinationPrefixes("/pub")
    }

    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        registration.interceptors(object : ChannelInterceptor {
            override fun preSend(message: Message<*>, channel: MessageChannel): Message<*> {
                val accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor::class.java)!!

                if (accessor.command == StompCommand.CONNECT) {
                    val authToken = accessor.getNativeHeader("Authorization")?.firstOrNull()
                    if (authToken != null && authToken.startsWith("Bearer ")) {
                        val token = authToken.removePrefix("Bearer ")
                        val auth = jwtUtils.getAuthentication(token)
                        SecurityContextHolder.getContext().authentication = auth

                        val userId:String = (auth.principal as JwtUserDetails).id?.value.toString()
                        accessor.setLeaveMutable(true)
                        accessor.setHeader("user-id", userId)
                        return MessageBuilder.createMessage(message.payload, accessor.messageHeaders)
                    }
                } else if (accessor.command == StompCommand.SEND) {
                    val auth = SecurityContextHolder.getContext().authentication
                    val userId:String = (auth.principal as JwtUserDetails).id?.value.toString()

                    accessor.setLeaveMutable(true)
                    accessor.setHeader("user-id", userId)
                    return MessageBuilder.createMessage(message.payload, accessor.messageHeaders)
                }

                return message
            }
        })
    }
}