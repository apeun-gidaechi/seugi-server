package seugi.server.domain.chat.websocket.dto

import org.springframework.web.socket.WebSocketSession
import java.util.*


class ChatRoomDTO{
    var roomId: String? = null
    var name: String? = null
    val sessions: Set<WebSocketSession> = HashSet()

    companion object {
        fun create(name: String?): ChatRoomDTO {
            val room = ChatRoomDTO()
            room.roomId = UUID.randomUUID().toString()
            room.name = name
            return room
        }
    }

}