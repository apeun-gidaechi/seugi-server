package seugi.server.domain.chat.websocket.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import seugi.server.domain.chat.websocket.handler.WebSocketHandler

@Configuration
class WebSocketConfig(
    private val chatHandler: WebSocketHandler
) : WebSocketConfigurer {

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(chatHandler, "ws/chat").setAllowedOrigins("*")
    }
}