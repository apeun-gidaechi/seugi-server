package seugi.server.domain.chat.websocket.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ChatController {
    @GetMapping("/chat")
    fun chatGET() : String {
        println("@ChatController chat GET()")
        return "chat"
    }
}