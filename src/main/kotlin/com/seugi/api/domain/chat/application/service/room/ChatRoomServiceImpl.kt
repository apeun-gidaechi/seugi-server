package com.seugi.api.domain.chat.application.service.room

import com.seugi.api.domain.chat.application.service.joined.JoinedService
import com.seugi.api.domain.chat.domain.enums.status.ChatStatusEnum
import com.seugi.api.domain.chat.domain.enums.type.RoomType
import com.seugi.api.domain.chat.domain.enums.type.RoomType.GROUP
import com.seugi.api.domain.chat.domain.enums.type.RoomType.PERSONAL
import com.seugi.api.domain.chat.domain.joined.JoinedEntity
import com.seugi.api.domain.chat.domain.joined.model.Joined
import com.seugi.api.domain.chat.domain.room.ChatRoomEntity
import com.seugi.api.domain.chat.domain.room.ChatRoomRepository
import com.seugi.api.domain.chat.domain.room.mapper.RoomMapper
import com.seugi.api.domain.chat.domain.room.model.Room
import com.seugi.api.domain.chat.presentation.room.dto.request.CreateRoomRequest
import com.seugi.api.domain.member.adapter.out.repository.MemberRepository
import com.seugi.api.global.response.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ChatRoomServiceImpl(
    private val chatRoomRepository: ChatRoomRepository,
    private val memberRepository: MemberRepository,
    private val joinedService: JoinedService,
    private val chatRoomMapper: RoomMapper
) : ChatRoomService {

    @Transactional
    override fun createChatRoom(createRoomRequest: CreateRoomRequest, userId:Long, type: RoomType): BaseResponse<Long> {

        if(type == GROUP && createRoomRequest.roomName.isEmpty()){
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
            status = HttpStatus.OK.value(),
            success = true,
            state = "C1",
            message = "채팅방 생성 성공 | 채팅방 ID",
            data = chatRoomId
        )
    }

    @Transactional(readOnly = true)
    override fun getRooms(userId: Long, type: RoomType): BaseResponse<List<Room>> {

        val joined : List<Joined> = joinedService.findByJoinedUserId(userId, type)
        val rooms : List<Optional<ChatRoomEntity>> = joined.map { chatRoomRepository.findById(it.chatRoomId)}
        val data = rooms.filter { it.isPresent }.map { it.get() }.map { chatRoomMapper.toDomain(it) }

        return BaseResponse(
            status = HttpStatus.OK.value(),
            success = true,
            state = "C1",
            message = "방 찾기 성공",
            data = data
        )

    }

    @Transactional
    override fun leftRoom(userId: Long, roomId: Long): BaseResponse<Unit> {

        val joinedEntity: JoinedEntity = joinedService.findByRoomId(roomId)
        joinedEntity.joinedUserId -= userId

        if (joinedEntity.joinedUserId.isEmpty()){
            val chatRoomEntity: ChatRoomEntity = chatRoomRepository.findById(roomId).get()
            chatRoomEntity.chatStatus = ChatStatusEnum.DELETE
            chatRoomRepository.save(chatRoomEntity)
        }

        joinedService.save(joinedEntity)

        return BaseResponse(
            status = HttpStatus.OK.value(),
            state = "J1",
            success = true,
            message = "방 나가기 성공"
        )
    }

}