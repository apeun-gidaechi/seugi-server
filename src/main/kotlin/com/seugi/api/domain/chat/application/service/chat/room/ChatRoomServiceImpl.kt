package com.seugi.api.domain.chat.application.service.chat.room

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
import com.seugi.api.domain.chat.presentation.chat.room.dto.request.CreateRoomRequest
import com.seugi.api.domain.chat.presentation.chat.room.dto.request.SearchRoomRequest
import com.seugi.api.domain.chat.presentation.chat.room.dto.response.RoomResponse
import com.seugi.api.domain.chat.presentation.websocket.dto.ChatMessageDto
import com.seugi.api.domain.member.adapter.`in`.dto.res.RetrieveMemberResponse
import com.seugi.api.domain.member.application.port.out.LoadMemberPort
import com.seugi.api.global.exception.CustomException
import com.seugi.api.global.response.BaseResponse
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatRoomServiceImpl(
    private val chatRoomRepository: ChatRoomRepository,
    private val loadMemberPort: LoadMemberPort,
    private val chatRoomMapper: RoomMapper,
    private val messageService: MessageService
) : ChatRoomService {

    @Transactional(readOnly = true)
    protected fun findChatRoomById(id: String): ChatRoomEntity {
        if (id.length != 24) throw CustomException(ChatErrorCode.CHAT_ROOM_ID_ERROR)
        return chatRoomRepository.findById(ObjectId(id))
            .orElseThrow { CustomException(ChatErrorCode.CHAT_ROOM_NOT_FOUND) }
    }

    private fun toResponse(chatRoomEntity: ChatRoomEntity, userId: Long): RoomResponse {
        val lastMessageEntity = messageService.getMessage(chatRoomEntity.id.toString())
        return chatRoomMapper.toResponse(
            room = chatRoomMapper.toDomain(chatRoomEntity),
            members = getUserInfo(chatRoomEntity),
            lastMessage = lastMessageEntity?.message ?: "",
            lastMessageTimeStamp = (lastMessageEntity?.timestamp ?: "").toString(),
            notReadCnt = messageService.getNotReadMessageCount(chatRoomEntity.id.toString(), userId)
        )
    }

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

        createRoomRequest.joinUsers.add(userId)

        if (
            type == PERSONAL && createRoomRequest.joinUsers.size > 2
            || type == GROUP && createRoomRequest.joinUsers.size < 2
        ) throw CustomException(
            ChatErrorCode.CHAT_ROOM_CREATE_ERROR
        )

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
            message = "채팅방 생성 성공 | 채팅방 ID",
            data = chatRoomId
        )
    }

    @Transactional(readOnly = true)
    override fun getRoom(roomId: String, userId: Long, type: RoomType): BaseResponse<RoomResponse> {

        val data = findChatRoomById(roomId)

        if (!data.joinedUserId.contains(userId)) throw CustomException(ChatErrorCode.NO_ACCESS_ROOM)

        when (type) {
            PERSONAL -> {
                data.apply {
                    val member = loadMemberPort.loadMemberWithId(joinedUserId.filter { id -> id != userId }[0])
                    chatName = member.name.value
                    chatRoomImg = member.picture.value
                }
                return BaseResponse(
                    message = "채팅방 단건 조회성공!",
                    data = toResponse(data, userId)
                )
            }

            GROUP -> {
                return BaseResponse(
                    message = "채팅방 단건 조회성공!",
                    data = toResponse(data, userId)
                )
            }
        }
    }


    @Transactional(readOnly = true)
    override fun getRooms(workspaceId: String, userId: Long, type: RoomType): BaseResponse<List<RoomResponse>> {

        val chatRoomEntity =
            chatRoomRepository.findByWorkspaceIdEqualsAndChatStatusEqualsAndRoomTypeAndJoinedUserIdContains(
                workspaceId = workspaceId,
                chatStatus = ChatStatusEnum.ALIVE,
                roomType = type,
                joinedUserId = userId
            ).orEmpty()

        when (type) {
            PERSONAL -> {
                val rooms: List<ChatRoomEntity> = chatRoomEntity.map {

                    val room = findChatRoomById(it.id.toString())
                    room.apply {
                        val member = loadMemberPort.loadMemberWithId(it.joinedUserId.filter { id -> id != userId }[0])
                        chatName = member.name.value
                        chatRoomImg = member.picture.value
                    }
                }

                return BaseResponse(
                    message = "채팅방 불러오기 성공",
                    data = rooms.map { toResponse(it, userId) }
                )
            }

            GROUP -> return BaseResponse(
                message = "채팅방 불러오기 성공",
                data = chatRoomEntity.map { toResponse(it, userId) }
            )
        }
    }

    @Transactional
    override fun leftRoom(userId: Long, roomId: String): BaseResponse<Unit> {

        val chatRoomEntity = findChatRoomById(roomId)

        //방장인지 확인하는 로직, 방장일경우 못나감 하지만 방 인원이 자신뿐이라면 넘어감
        if (chatRoomEntity.roomAdmin == userId && chatRoomEntity.joinedUserId.size != 1)
            throw CustomException(ChatErrorCode.CHAT_LEFT_ERROR)

        //그룹채팅방만 나갈 수 있음
        if (chatRoomEntity.roomType != GROUP) throw CustomException(ChatErrorCode.CHAT_TYPE_ERROR)

        chatRoomEntity.joinedUserId -= userId

        if (chatRoomEntity.joinedUserId.isEmpty()) {
            chatRoomEntity.chatStatus = ChatStatusEnum.DELETE
        }

        chatRoomRepository.save(chatRoomEntity)

        messageService.sendAndSaveMessage(
            chatMessageDto = ChatMessageDto(
                type = Type.LEFT,
                roomId = roomId,
                message = "",
                eventList = setOf(userId)
            ),
            userId = userId
        )


        return BaseResponse(
            message = "방 나가기 성공"
        )
    }

    @Transactional(readOnly = true)
    override fun searchRoomNameIn(
        searchRoomRequest: SearchRoomRequest,
        type: RoomType,
        userId: Long
    ): BaseResponse<List<RoomResponse>> {

        val chatRoomEntity =
            chatRoomRepository.findByWorkspaceIdEqualsAndChatStatusEqualsAndRoomTypeAndJoinedUserIdContains(
                workspaceId = searchRoomRequest.workspaceId,
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
                    message = "채팅방 검색 성공",
                    data = entity.map {
                        toResponse(it, userId)
                    }
                )
            }

            GROUP -> return BaseResponse(
                message = "채팅방 검색 성공",
                data = chatRoomEntity.map {
                    toResponse(it, userId)
                }
            )

        }


    }

}