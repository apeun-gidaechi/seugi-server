package com.seugi.api.domain.chat.presentation.websocket.config

import com.seugi.api.domain.chat.application.service.chat.room.ChatRoomService
import com.seugi.api.domain.member.application.exception.MemberErrorCode
import com.seugi.api.global.auth.jwt.JwtUserDetails
import com.seugi.api.global.auth.jwt.JwtUtils
import com.seugi.api.global.exception.CustomException
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.SimpAttributesContextHolder
import org.springframework.messaging.simp.SimpMessageType
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageBuilder
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.util.AntPathMatcher
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer


@Configuration
@EnableWebSocketMessageBroker
class StompWebSocketConfig(
    private val jwtUtils: JwtUtils,
    private val chatRoomService: ChatRoomService,
    @Value("\${spring.rabbitmq.host}") private val rabbitmqHost: String,
) : WebSocketMessageBrokerConfigurer {

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/stomp/chat")
            .setAllowedOrigins("*")
    }

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.setPathMatcher(AntPathMatcher("."))
        registry.setApplicationDestinationPrefixes("/pub")
        registry.enableStompBrokerRelay("/queue", "/topic", "/exchange", "/amq/queue")
            .setRelayHost(rabbitmqHost)
            .setVirtualHost("/")
    }

    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        registration.interceptors(object : ChannelInterceptor {
            override fun preSend(message: Message<*>, channel: MessageChannel): Message<*> {
                val accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor::class.java)!!

                when (accessor.messageType) {
                    SimpMessageType.CONNECT -> handleConnect(message, accessor)
                    SimpMessageType.SUBSCRIBE -> handleSubscribe(accessor)
                    SimpMessageType.UNSUBSCRIBE, SimpMessageType.DISCONNECT -> handleUnsubscribeOrDisconnect()
                    else -> {}
                }
                return message
            }
        })
    }

    private fun handleConnect(message: Message<*>, accessor: StompHeaderAccessor) {
        val authToken = accessor.getNativeHeader("Authorization")?.firstOrNull()
        if (authToken != null && authToken.startsWith("Bearer ")) {
            val auth = jwtUtils.getAuthentication(authToken)
            val userDetails = auth.principal as? JwtUserDetails
            val userId: String? = userDetails?.id?.value?.toString()

            if (userId != null) {
                val simpAttributes = SimpAttributesContextHolder.currentAttributes()
                simpAttributes.setAttribute("user-id", userId)
                MessageBuilder.createMessage(message.payload, accessor.messageHeaders)
            } else {
                throw CustomException(MemberErrorCode.MEMBER_NOT_FOUND)
            }
        }
    }

    private fun handleSubscribe(accessor: StompHeaderAccessor) {
        accessor.destination?.let {
            val simpAttributes = SimpAttributesContextHolder.currentAttributes()
            simpAttributes.setAttribute("sub", it.substringAfterLast("."))
            val userId = simpAttributes.getAttribute("user-id") as String
            chatRoomService.sub(
                userId = userId.toLong(),
                roomId = it.substringAfterLast(".")
            )
        }
    }

    private fun handleUnsubscribeOrDisconnect() {
        val simpAttributes = SimpAttributesContextHolder.currentAttributes()
        val userId = simpAttributes.getAttribute("user-id") as String?
        val roomId = simpAttributes.getAttribute("sub") as String?
        userId?.let {
            roomId?.let {
                chatRoomService.unSub(
                    userId = userId.toLong(),
                    roomId = it
                )
            }
        }
    }
}