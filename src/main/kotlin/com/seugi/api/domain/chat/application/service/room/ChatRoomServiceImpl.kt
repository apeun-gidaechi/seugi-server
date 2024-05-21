package com.seugi.api.domain.chat.application.service.room

import com.seugi.api.domain.chat.application.service.joined.JoinedService
import com.seugi.api.domain.chat.application.service.message.MessageService
import com.seugi.api.domain.chat.domain.chat.model.Type
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
import com.seugi.api.domain.chat.exception.ChatErrorCode
import com.seugi.api.domain.chat.presentation.room.dto.request.CreateRoomRequest
import com.seugi.api.domain.chat.presentation.room.dto.request.SearchRoomRequest
import com.seugi.api.domain.chat.presentation.websocket.dto.ChatMessageDto
import com.seugi.api.domain.member.adapter.out.repository.MemberRepository
import com.seugi.api.global.exception.CustomException
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
    private val chatRoomMapper: RoomMapper,
    private val messageService: MessageService
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
            joinedUserId = createRoomRequest.joinUsers!!,
            workspaceId = createRoomRequest.workspaceId,
        )

        messageService.sendAndSaveMessage(
            chatMessageDto = ChatMessageDto(
                type = Type.ENTER,
                roomId = chatRoomId,
                message = "",
                eventList = createRoomRequest.joinUsers,
            ),
            userId = userId
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
    override fun getRooms(workspaceId: String, userId: Long, type: RoomType): BaseResponse<List<Room>> {

        val joined : List<Joined> = joinedService.findByJoinedUserId(workspaceId, userId, type)

        when(type){
            PERSONAL -> {
                val rooms: List<ChatRoomEntity> = joined.map {
                    val room = chatRoomRepository.findById(it.chatRoomId).orElseThrow{CustomException(ChatErrorCode.CHAT_ROOM_NOT_FOUND)}
                    room.apply {
                        val member = memberRepository.findById(it.joinUserId.filter { id -> id != userId }[0]).get()
                        chatName = member.name
                        chatRoomImg = member.picture
                    }
                }
                return BaseResponse(
                    status = HttpStatus.OK.value(),
                    success = true,
                    state = "C1",
                    message = "방 찾기 성공",
                    data = rooms.map { chatRoomMapper.toDomain(it) }
                )
            }

            GROUP -> {
                val rooms : List<Optional<ChatRoomEntity>> = joined.map { chatRoomRepository.findById(it.chatRoomId)}
                return BaseResponse(
                    status = HttpStatus.OK.value(),
                    success = true,
                    state = "C1",
                    message = "방 찾기 성공",
                    data = rooms.filter { it.isPresent }.map { it.get() }.map { chatRoomMapper.toDomain(it) }
                )
            }

        }

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

        val eventList: List<Long> = listOf(userId)

        messageService.sendAndSaveMessage(
            chatMessageDto = ChatMessageDto(
                type = Type.LEFT,
                roomId = roomId,
                message = "",
                eventList = eventList.toMutableList()
            ),
            userId = userId
        )


        return BaseResponse(
            status = HttpStatus.OK.value(),
            state = "J1",
            success = true,
            message = "방 나가기 성공"
        )
    }

    @Transactional(readOnly = true)
    override fun searchRoomNameIn(searchRoomRequest: SearchRoomRequest, type: RoomType, userId: Long): BaseResponse<List<Room>> {
        val joined: List<Joined> = joinedService.findByJoinedUserId(userId = userId, roomType = type, workspaceId = searchRoomRequest.workspaceId)

        when(type){
            PERSONAL -> {
                return BaseResponse(
                    status = HttpStatus.OK.value(),
                    state = "OK",
                    success = true,
                    message = "방 찾기 성공",
                    data = joined.mapNotNull {
                        val name = memberRepository.findById(it.joinUserId.firstOrNull { id -> id != userId }!!).get().name
                        if (name.contains(searchRoomRequest.word)) {
                            val chatRoom = chatRoomRepository.findById(it.chatRoomId).orElseThrow{CustomException(ChatErrorCode.CHAT_ROOM_NOT_FOUND)}
                            chatRoom.chatName = name
                            chatRoomMapper.toDomain(chatRoom)
                        } else {
                            null
                        }
                    }
                )
            }
            GROUP -> {
                return BaseResponse(
                    status = HttpStatus.OK.value(),
                    state = "OK",
                    success = true,
                    message = "방 찾기 성공",
                    data = joined.mapNotNull { chatRoomRepository.searchRoom(it.chatRoomId, searchRoomRequest.word) }
                )
            }

        }


    }

}