package seugi.server.domain.chat.websocket.repository

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Repository
import seugi.server.domain.chat.websocket.dto.ChatRoomDTO
import java.util.*


@Repository
class ChatRoomRepository {
    private var chatRoomDTOMap: MutableMap<String, ChatRoomDTO?>? = null

    @PostConstruct
    private fun init() {
        chatRoomDTOMap = LinkedHashMap()
    }

    fun findAllRooms(): List<ChatRoomDTO?> {
        val result: List<ChatRoomDTO?> = ArrayList(chatRoomDTOMap!!.values)
        Collections.reverse(result)

        return result
    }

    fun findRoomById(id: String): ChatRoomDTO? {
        return chatRoomDTOMap!![id]
    }

    fun createChatRoomDTO(name: String?): ChatRoomDTO {
        val room = ChatRoomDTO.create(name)
        chatRoomDTOMap?.put(room.roomId!!, room)

        return room
    }
}