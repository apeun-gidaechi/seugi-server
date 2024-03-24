package seugi.server.domain.chat.websocket.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import seugi.server.domain.chat.websocket.dto.ChatRoomDTO
import seugi.server.domain.chat.websocket.repository.ChatRoomRepository

@Controller
@RequestMapping("/chat")
class RoomController (
    val repository: ChatRoomRepository
){
    //채팅방 목록 조회
    @GetMapping("/rooms")
    fun rooms() : List<ChatRoomDTO?> {
        return repository.findAllRooms()
    }

    //채팅방 개설
    @PostMapping("/room")
    fun create(@RequestParam name:String) : ChatRoomDTO{
        val chatRoomDTO = repository.createChatRoomDTO(name)
        return chatRoomDTO
    }

    //채팅방 조회
    @GetMapping("/room/{id}")
    fun getRoom(@PathVariable id: String): ChatRoomDTO? {
        return repository.findRoomById(id)
    }
}