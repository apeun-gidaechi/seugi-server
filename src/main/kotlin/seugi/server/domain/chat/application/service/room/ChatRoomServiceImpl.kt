package seugi.server.domain.chat.application.service.room

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import seugi.server.domain.chat.application.service.joined.JoinedService
import seugi.server.domain.chat.domain.room.ChatRoomRepository
import seugi.server.domain.chat.domain.room.mapper.RoomMapper
import seugi.server.domain.chat.presentation.room.dto.request.CreateRoomRequest
import seugi.server.global.response.BaseResponse

@Service
class ChatRoomServiceImpl(
    private val chatRoomRepository: ChatRoomRepository,
    private val joinedService: JoinedService,
    private val chatRoomMapper: RoomMapper
) : ChatRoomService {

    override fun createChatRoom(createRoomRequest: CreateRoomRequest): BaseResponse<Unit> {
        val chatRoomId = chatRoomRepository.save(
            chatRoomMapper.toEntity(
                chatRoomMapper.toRoom(createRoomRequest)
            )
        ).id

        joinedService.joinUserJoined(
            chatRoomId = chatRoomId!!,
            joinedUserId = createRoomRequest.joinUsers!!
        )

        return BaseResponse(
            status = HttpStatus.OK,
            success = true,
            state = "C1",
            message = "채팅방 생성 성공"
        )
    }

}