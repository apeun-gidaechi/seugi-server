package seugi.server.domain.chat.application.service.room

import org.springframework.stereotype.Service
import seugi.server.domain.chat.domain.room.ChatRoomRepository
import seugi.server.domain.chat.domain.room.mapper.RoomMapper
import seugi.server.domain.chat.domain.room.model.Room
import seugi.server.domain.chat.presentation.room.dto.request.CreateRoomRequest

@Service
class ChatRoomServiceImpl(
    private val chatRoomRepository: ChatRoomRepository,
    private val chatRoomMapper: RoomMapper
) : ChatRoomService {

    override fun createChatRoom(createRoomRequest: CreateRoomRequest) {
        chatRoomRepository.save(chatRoomMapper.toEntity(chatRoomMapper.toRoom(createRoomRequest)))
    }

}