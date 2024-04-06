package seugi.server.domain.chat.application.service.room

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import seugi.server.domain.chat.application.service.joined.JoinedService
import seugi.server.domain.chat.domain.enums.type.RoomType
import seugi.server.domain.chat.domain.enums.type.RoomType.GROUP
import seugi.server.domain.chat.domain.enums.type.RoomType.PERSONAL
import seugi.server.domain.chat.domain.joined.model.Joined
import seugi.server.domain.chat.domain.room.ChatRoomEntity
import seugi.server.domain.chat.domain.room.ChatRoomRepository
import seugi.server.domain.chat.domain.room.mapper.RoomMapper
import seugi.server.domain.chat.domain.room.model.Room
import seugi.server.domain.chat.presentation.room.dto.request.CreateRoomRequest
import seugi.server.domain.member.adapter.out.repository.MemberRepository
import seugi.server.global.response.BaseResponse
import java.util.*

@Service
class ChatRoomServiceImpl(
    private val chatRoomRepository: ChatRoomRepository,
    private val memberRepository: MemberRepository,
    private val joinedService: JoinedService,
    private val chatRoomMapper: RoomMapper
) : ChatRoomService {

    override fun createChatRoom(createRoomRequest: CreateRoomRequest, userId:Long, type: RoomType): BaseResponse<Long> {

        when(type){
            PERSONAL ->
                createRoomRequest.roomName = memberRepository.findById(createRoomRequest.joinUsers?.first()?.toLong()!!).get().name
            GROUP ->
                if (createRoomRequest.roomName.isEmpty()){
                    createRoomRequest.roomName = createRoomRequest.joinUsers?.asSequence()
                        ?.map { memberRepository.findById(it).get().name }
                        ?.takeWhile { (createRoomRequest.roomName + it).length <= 10 }
                        ?.joinToString(separator = ", ")
                        ?: createRoomRequest.roomName

                }
        }

        createRoomRequest.joinUsers?.add(userId)
        val chatRoomId = chatRoomRepository.save(
            chatRoomMapper.toEntity(
                chatRoomMapper.toRoom(createRoomRequest, type)
            )
        ).id

        joinedService.joinUserJoined(
            chatRoomId = chatRoomId!!,
            roomAdmin = if (type == GROUP) userId else 0,
            type = type,
            joinedUserId = createRoomRequest.joinUsers!!
        )

        return BaseResponse(
            status = HttpStatus.OK,
            success = true,
            state = "C1",
            message = "채팅방 생성 성공 | 채팅방 ID",
            data = chatRoomId
        )
    }

    override fun searchRooms(userId: Long, type: RoomType): BaseResponse<List<Room>> {

        val joined : List<Joined> = joinedService.findByJoinedUserId(userId, type)
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