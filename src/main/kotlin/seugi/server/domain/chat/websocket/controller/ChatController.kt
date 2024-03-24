package seugi.server.domain.chat.websocket.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/health")
class ChatController {
    @PostMapping("/check")
    fun chatGET() : String {
        return "running"
    }
}