package seugi.server.domain.chat.application.service.room

import org.springframework.stereotype.Service
import seugi.server.domain.chat.domain.room.ChatRoomRepository
import seugi.server.domain.chat.presentation.room.dto.request.CreateRoomRequest

@Service
class ChatRoomServiceImpl(
    private val chatRoomRepository: ChatRoomRepository
) : ChatRoomService {
    override fun createChatRoom(createRoomRequest: CreateRoomRequest) {
        TODO("채팅방 생성 로직 구현")
    }
}