package seugi.server.domain.chat.websocket.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import seugi.server.domain.chat.websocket.repository.ChatRoomRepository

@Controller
@RequestMapping("/chat")
class RoomController (
    val repository: ChatRoomRepository
){
    //채팅방 목록 조회
    @GetMapping("/rooms")
    fun rooms() : ModelAndView{
        val mv : ModelAndView = ModelAndView("chat/rooms")
        mv.addObject("list", repository.findAllRooms())

        return mv
    }

    //채팅방 개설
    @PostMapping("/room")
    fun create(@RequestParam name:String, rttr : RedirectAttributes) : String{
        println("name : $name")
        rttr.addFlashAttribute("roomName", repository.createChatRoomDTO(name))
        return "redirect:/chat/rooms"
    }

    //채팅방 조회
    @GetMapping("/room")
    fun getRoom(roomId: String , model:Model) {
        println("room id : $roomId")
        model.addAttribute("room", repository.findRoomById(roomId))
    }
}