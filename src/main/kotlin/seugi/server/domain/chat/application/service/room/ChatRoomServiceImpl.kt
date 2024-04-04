package seugi.server.domain.chat.application.service.room

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import seugi.server.domain.chat.application.service.joined.JoinedService
import seugi.server.domain.chat.domain.joined.model.Joined
import seugi.server.domain.chat.domain.room.ChatRoomEntity
import seugi.server.domain.chat.domain.room.ChatRoomRepository
import seugi.server.domain.chat.domain.room.mapper.RoomMapper
import seugi.server.domain.chat.domain.room.model.Room
import seugi.server.domain.chat.presentation.room.dto.request.CreateRoomRequest
import seugi.server.global.response.BaseResponse
import java.util.*

@Service
class ChatRoomServiceImpl(
    private val chatRoomRepository: ChatRoomRepository,
    private val joinedService: JoinedService,
    private val chatRoomMapper: RoomMapper
) : ChatRoomService {

    override fun createChatRoom(createRoomRequest: CreateRoomRequest, userId:Long): BaseResponse<Unit> {
        createRoomRequest.joinUsers?.add(userId)
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
            message = "채팅방 생성 성공 | 채팅방 ID = $chatRoomId"
        )
    }

    override fun searchRooms(userId: Long): BaseResponse<List<Room>> {

        val joined : List<Joined> = joinedService.findByJoinedUserId(userId)
        val rooms : List<Optional<ChatRoomEntity>> = joined.map { chatRoomRepository.findById(it.chatRoomId)}
        val data = rooms.filter { it.isPresent }.map { it.get() }.map { chatRoomMapper.toDomain(it) }

        return BaseResponse(
            status = HttpStatus.OK,
            success = true,
            state = "C1",
            message = "방 찾기 성공",
            data = data
        )

    }

}