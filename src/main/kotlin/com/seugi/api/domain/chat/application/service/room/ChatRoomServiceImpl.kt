package com.seugi.api.domain.chat.application.service.room

import com.seugi.api.domain.chat.application.service.message.MessageService
import com.seugi.api.domain.chat.domain.chat.model.Type
import com.seugi.api.domain.chat.domain.enums.status.ChatStatusEnum
import com.seugi.api.domain.chat.domain.enums.type.RoomType
import com.seugi.api.domain.chat.domain.enums.type.RoomType.GROUP
import com.seugi.api.domain.chat.domain.enums.type.RoomType.PERSONAL
import com.seugi.api.domain.chat.domain.room.ChatRoomEntity
import com.seugi.api.domain.chat.domain.room.ChatRoomRepository
import com.seugi.api.domain.chat.domain.room.mapper.RoomMapper
import com.seugi.api.domain.chat.exception.ChatErrorCode
import com.seugi.api.domain.chat.presentation.room.dto.request.CreateRoomRequest
import com.seugi.api.domain.chat.presentation.room.dto.request.SearchRoomRequest
import com.seugi.api.domain.chat.presentation.room.dto.response.RoomResponse
import com.seugi.api.domain.chat.presentation.websocket.dto.ChatMessageDto
import com.seugi.api.domain.member.adapter.`in`.dto.res.RetrieveMemberResponse
import com.seugi.api.domain.member.application.port.out.LoadMemberPort
import com.seugi.api.global.exception.CustomException
import com.seugi.api.global.response.BaseResponse
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatRoomServiceImpl(
    private val chatRoomRepository: ChatRoomRepository,
    private val loadMemberPort: LoadMemberPort,
    private val chatRoomMapper: RoomMapper,
    private val messageService: MessageService
) : ChatRoomService {

    //채팅방 반환시 유저 모델 전달용 함수
    private fun getUserInfo(chatRoomEntity: ChatRoomEntity): Set<RetrieveMemberResponse> {
        return chatRoomEntity.joinedUserId.map { RetrieveMemberResponse(loadMemberPort.loadMemberWithId(it)) }
            .toSet()
    }

    @Transactional
    override fun createChatRoom(
        createRoomRequest: CreateRoomRequest,
        userId: Long,
        type: RoomType
    ): BaseResponse<String> {

        if (
            type == PERSONAL && createRoomRequest.joinUsers.size >= 2
            || type == GROUP && createRoomRequest.joinUsers.size <= 2
        ) throw CustomException(
            ChatErrorCode.CHAT_ROOM_CREATE_ERROR
        )

        createRoomRequest.joinUsers.map {
            loadMemberPort.loadMemberWithId(it)
        }

        if (type == GROUP && createRoomRequest.roomName.isEmpty()) {
            createRoomRequest.roomName = createRoomRequest.joinUsers.asSequence()
                .map { loadMemberPort.loadMemberWithId(it).name.value }
                .takeWhile { (createRoomRequest.roomName + it).length <= 10 }
                .joinToString(separator = ", ")
        }

        val chatRoomId = chatRoomRepository.save(
            chatRoomMapper.toEntity(
                chatRoomMapper.toRoom(
                    createRoomRequest = createRoomRequest,
                    type = type,
                    userId = userId
                )
            )
        ).id.toString()

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
            state = "OK",
            message = "채팅방 생성 성공 | 채팅방 ID",
            data = chatRoomId
        )
    }

    @Transactional(readOnly = true)
    override fun getRoom(roomId: String, userId: Long): BaseResponse<RoomResponse> {

        //ObjectId는 24글자 고정이라 예외처리 로직 추가
        if (roomId.length != 24) throw CustomException(ChatErrorCode.CHAT_SEARCH_ERROR)

        val data = chatRoomRepository.findById(ObjectId(roomId))
            .orElseThrow { CustomException(ChatErrorCode.CHAT_ROOM_NOT_FOUND) }

        if (!data.joinedUserId.contains(userId)) throw CustomException(ChatErrorCode.NO_ACCESS_ROOM)

        data.chatName

        return BaseResponse(
            status = HttpStatus.OK.value(),
            state = "OK",
            success = true,
            message = "채팅방 단건 조회성공!",
            data = chatRoomMapper.toResponse(
                room = chatRoomMapper.toDomain(data),
                members = getUserInfo(data)
            )
        )

    }


    @Transactional(readOnly = true)
    override fun getRooms(workspaceId: String, userId: Long, type: RoomType): BaseResponse<List<RoomResponse>> {

        val chatRoomEntity =
            chatRoomRepository.findByWorkspaceIDEqualsAndChatStatusEqualsAndRoomTypeAndJoinedUserIdContains(
                workspaceID = workspaceId,
                chatStatus = ChatStatusEnum.ALIVE,
                roomType = type,
                joinedUserId = userId
            ) ?: throw CustomException(ChatErrorCode.CHAT_ROOM_NOT_FOUND)

        when (type) {
            PERSONAL -> {
                val rooms: List<ChatRoomEntity> = chatRoomEntity.map {

                    val room = chatRoomRepository.findById(it.id!!)
                        .orElseThrow { CustomException(ChatErrorCode.CHAT_ROOM_NOT_FOUND) }
                    room.apply {
                        val member = loadMemberPort.loadMemberWithId(it.joinedUserId.filter { id -> id != userId }[0])
                        chatName = member.name.value
                        chatRoomImg = member.picture.value
                    }
                }
                return BaseResponse(
                    status = HttpStatus.OK.value(),
                    success = true,
                    state = "C1",
                    message = "방 찾기 성공",
                    data = rooms.map {
                        chatRoomMapper.toResponse(
                            room = chatRoomMapper.toDomain(it),
                            members = getUserInfo(it)
                        )
                    }
                )
            }

            GROUP -> {
                return BaseResponse(
                    status = HttpStatus.OK.value(),
                    success = true,
                    state = "C1",
                    message = "방 찾기 성공",
                    data = chatRoomEntity.map {
                        chatRoomMapper.toResponse(
                            room = chatRoomMapper.toDomain(it),
                            members = getUserInfo(it)
                        )
                    }
                )
            }
        }
    }

    //    @Transactional
//    override fun leftRoom(userId: Long, roomId: Long): BaseResponse<Unit> {
//
//        val joinedEntity: JoinedEntity = joinedService.findByRoomId(roomId)
//        joinedEntity.joinedUserId -= userId
//
//        if (joinedEntity.joinedUserId.isEmpty()) {
//            val chatRoomEntity: ChatRoomEntity = chatRoomRepository.findById(roomId).get()
//            chatRoomEntity.chatStatus = ChatStatusEnum.DELETE
//            chatRoomRepository.save(chatRoomEntity)
//        }
//
//        joinedService.save(joinedEntity)
//
//        val eventList: List<Long> = listOf(userId)
//
//        messageService.sendAndSaveMessage(
//            chatMessageDto = ChatMessageDto(
//                type = Type.LEFT,
//                roomId = roomId,
//                message = "",
//                eventList = eventList.toMutableList()
//            ),
//            userId = userId
//        )
//
//
//        return BaseResponse(
//            status = HttpStatus.OK.value(),
//            state = "J1",
//            success = true,
//            message = "방 나가기 성공"
//        )
//    }
//
    @Transactional(readOnly = true)
    override fun searchRoomNameIn(
        searchRoomRequest: SearchRoomRequest,
        type: RoomType,
        userId: Long
    ): BaseResponse<List<RoomResponse>> {

        val chatRoomEntity =
            chatRoomRepository.findByWorkspaceIDEqualsAndChatStatusEqualsAndRoomTypeAndJoinedUserIdContains(
                workspaceID = searchRoomRequest.workspaceId,
                chatStatus = ChatStatusEnum.ALIVE,
                roomType = type,
                joinedUserId = userId
            ).orEmpty()

        when (type) {
            PERSONAL -> {

                val entity = chatRoomEntity.mapNotNull {
                    val member =
                        loadMemberPort.loadMemberWithId(it.joinedUserId.firstOrNull { id -> id != userId }!!)
                    if (member.name.value.contains(searchRoomRequest.word)) {
                        val chatRoom = chatRoomRepository.findById(it.id!!)
                            .orElseThrow { CustomException(ChatErrorCode.CHAT_ROOM_NOT_FOUND) }
                        chatRoom.chatName = member.name.value
                        chatRoom.chatRoomImg = member.picture.value
                        return@mapNotNull chatRoom
                    } else {
                        null
                    }
                }

                return BaseResponse(
                    status = HttpStatus.OK.value(),
                    state = "OK",
                    success = true,
                    message = "방 찾기 성공",
                    data = entity.map {
                        chatRoomMapper.toResponse(
                            room = chatRoomMapper.toDomain(it),
                            members = getUserInfo(it)
                        )
                    }
                )
            }

            GROUP -> {

                return BaseResponse(
                    status = HttpStatus.OK.value(),
                    state = "OK",
                    success = true,
                    message = "방 찾기 성공",
                    data = chatRoomEntity.map {
                        chatRoomMapper.toResponse(
                            room = chatRoomMapper.toDomain(it),
                            members = getUserInfo(it)
                        )

                    }
                )
            }

        }


    }

}