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
import com.seugi.api.domain.chat.domain.room.model.Room
import com.seugi.api.domain.chat.exception.ChatErrorCode
import com.seugi.api.domain.chat.presentation.chat.room.dto.request.CreateRoomRequest
import com.seugi.api.domain.chat.presentation.chat.room.dto.request.SearchRoomRequest
import com.seugi.api.domain.chat.presentation.chat.room.dto.response.RoomResponse
import com.seugi.api.domain.chat.presentation.websocket.dto.ChatMessageDto
import com.seugi.api.domain.chat.presentation.websocket.dto.MessageEventDto
import com.seugi.api.domain.member.presentation.controller.dto.res.RetrieveMemberResponse
import com.seugi.api.domain.member.service.MemberService
import com.seugi.api.global.exception.CustomException
import com.seugi.api.global.response.BaseResponse
import com.seugi.api.global.util.DateTimeUtil
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class ChatRoomServiceImpl(
    private val chatRoomRepository: ChatRoomRepository,
    private val memberService: MemberService,
    private val chatRoomMapper: RoomMapper,
    private val messageService: MessageService,
) : ChatRoomService {

    @Transactional(readOnly = true)
    override fun findChatRoomById(id: String): ChatRoomEntity {
        if (id.length != 24) throw CustomException(ChatErrorCode.CHAT_ROOM_ID_ERROR)
        return chatRoomRepository.findById(ObjectId(id))
            .orElseThrow { CustomException(ChatErrorCode.CHAT_ROOM_NOT_FOUND) }
    }

    private fun sendEventMessage(roomId: String, userId: Long, type: Type) {
        if (roomId != "message" && roomId.length == 24) {

            messageService.sendEventMessage(
                message = MessageEventDto(
                    userId = userId,
                    type = type
                ),
                roomId = roomId
            )
        }
    }

    @Transactional
    override fun sub(userId: Long, roomId: String) {

        sendEventMessage(roomId, userId, Type.SUB)

        val chatRoom = findChatRoomById(roomId)
        chatRoom.joinedUserInfo.find { it.userId == userId }?.timestamp = DateTimeUtil.localDateTime
        chatRoomRepository.save(chatRoom).joinedUserInfo.find { it.userId == userId }?.timestamp
    }

    @Transactional
    override fun unSub(userId: Long, roomId: String) {

        sendEventMessage(roomId, userId, Type.UNSUB)

        val chatRoom = findChatRoomById(roomId)
        chatRoom.joinedUserInfo.find { it.userId == userId }?.timestamp = LocalDateTime.now()
        chatRoomRepository.save(chatRoom).joinedUserInfo.find { it.userId == userId }?.timestamp
    }

    private fun toResponse(room: Room, userId: Long): RoomResponse {
        val lastMessageEntity = messageService.getLastMessage(room.id.toString())
        return chatRoomMapper.toResponse(
            room = room,
            members = getUserInfo(room),
            lastMessage = if (lastMessageEntity?.type == Type.MESSAGE) lastMessageEntity.message else "",
            lastMessageTimeStamp = (lastMessageEntity?.timestamp ?: LocalDateTime.now()).toString(),
            notReadCnt = messageService.getNotReadMessageCount(room, userId)
        )
    }

    //채팅방 반환시 유저 모델 전달용 함수
    private fun getUserInfo(room: Room): Set<RetrieveMemberResponse> {
        return room.joinUserInfo.map { RetrieveMemberResponse(memberService.findById(it.userId)) }
            .toSet()
    }

    private fun validateRoomCreation(createRoomRequest: CreateRoomRequest, type: RoomType) {
        if ((type == PERSONAL && createRoomRequest.joinUsers.size != 2) ||
            (type == GROUP && createRoomRequest.joinUsers.size < 2)
        ) {
            throw CustomException(ChatErrorCode.CHAT_ROOM_CREATE_ERROR)
        }
    }

    private fun generateRoomName(createRoomRequest: CreateRoomRequest): String {
        return createRoomRequest.joinUsers.asSequence()
            .map { memberService.findById(it).name }
            .takeWhile { (createRoomRequest.roomName + it).length <= 10 }
            .joinToString(separator = ", ")
    }

    @Transactional
    override fun createChatRoom(
        createRoomRequest: CreateRoomRequest,
        userId: Long,
        type: RoomType,
    ): BaseResponse<String> {

        createRoomRequest.joinUsers.add(userId)

        val existingChatRoom = chatRoomRepository.findByWorkspaceIdAndRoomTypeAndExactJoinedUserIds(
            workspaceId = createRoomRequest.workspaceId,
            joinedUserIds = createRoomRequest.joinUsers,
            userCount = createRoomRequest.joinUsers.size,
            roomType = PERSONAL
        )

        return if (existingChatRoom == null) {
            validateRoomCreation(createRoomRequest, type)
            if (type == GROUP && createRoomRequest.roomName.isEmpty()) {
                createRoomRequest.roomName = generateRoomName(createRoomRequest)
            }

            val newChatRoomId = chatRoomRepository.save(
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
                    roomId = newChatRoomId,
                    message = "",
                    eventList = createRoomRequest.joinUsers,
                ),
                userId = userId
            )

            BaseResponse(
                message = "채팅방 생성 성공 | 채팅방 ID",
                data = newChatRoomId
            )
        } else {
            BaseResponse(
                message = "채팅방 생성 성공 | 채팅방 ID",
                data = existingChatRoom.id.toString()
            )
        }
    }

    @Transactional(readOnly = true)
    override fun getRoom(roomId: String, userId: Long, type: RoomType): BaseResponse<RoomResponse> {

        val data = findChatRoomById(roomId)

        if (data.roomType != type) throw CustomException(ChatErrorCode.NO_TYPE_ROOM)

        if (data.joinedUserInfo.none { it.userId == userId }) {
            throw CustomException(ChatErrorCode.NO_ACCESS_ROOM)
        }

        val room = chatRoomMapper.toDomain(data)

        when (type) {
            PERSONAL -> {
                val member = memberService.findById(room.joinUserInfo.first { it.userId != userId }.userId)
                val chatRoom = room.copy(
                    chatName = member.name,
                    chatRoomImg = member.picture
                )


                return BaseResponse(
                    message = "채팅방 단건 조회성공!",
                    data = toResponse(chatRoom, userId)
                )
            }

            GROUP -> {
                return BaseResponse(
                    message = "채팅방 단건 조회성공!",
                    data = toResponse(room, userId)
                )
            }
        }
    }


    @Transactional(readOnly = true)
    override fun getRooms(workspaceId: String, userId: Long, type: RoomType): BaseResponse<List<RoomResponse>> {
        val chatRoomEntity = chatRoomRepository.findByWorkspaceIdAndChatStatusAndRoomTypeAndJoinedUserInfoUserId(
            workspaceId = workspaceId,
            chatStatus = ChatStatusEnum.ALIVE,
            roomType = type,
            userId = userId
        ).orEmpty()

        val chatRooms = chatRoomEntity.map { chatRoomMapper.toDomain(it) }

        if (chatRooms.isNotEmpty() && chatRoomEntity.first().roomType != type) throw CustomException(ChatErrorCode.NO_TYPE_ROOM)

        val rooms: List<RoomResponse> = when (type) {
            PERSONAL -> {
                chatRooms.map { room ->
                    val member = memberService.findById(
                        room.joinUserInfo.first { it.userId != userId }.userId
                    )
                    val updatedRoom = room.copy(
                        chatName = member.name,
                        chatRoomImg = member.picture
                    )
                    toResponse(updatedRoom, userId)
                }
            }

            else -> {
                chatRooms.map { toResponse(it, userId) }
            }
        }

        return BaseResponse(
            message = "채팅방 불러오기 성공",
            data = rooms
        )
    }

    @Transactional
    override fun leftRoom(userId: Long, roomId: String): BaseResponse<Unit> {

        val chatRoomEntity = findChatRoomById(roomId)

        //방장인지 확인하는 로직, 방장일경우 못나감 하지만 방 인원이 자신뿐이라면 넘어감
        if (chatRoomEntity.roomAdmin == userId && chatRoomEntity.joinedUserInfo.size != 1)
            throw CustomException(ChatErrorCode.CHAT_LEFT_ERROR)

        //그룹채팅방만 나갈 수 있음
        if (chatRoomEntity.roomType != GROUP) throw CustomException(ChatErrorCode.CHAT_TYPE_ERROR)

        chatRoomEntity.joinedUserInfo -= chatRoomEntity.joinedUserInfo.find { it.userId == userId }!!

        if (chatRoomEntity.joinedUserInfo.isEmpty()) {
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
        userId: Long,
    ): BaseResponse<List<RoomResponse>> {

        val chatRoomEntities =
            chatRoomRepository.findByWorkspaceIdAndChatStatusAndRoomTypeAndJoinedUserInfoUserId(
                workspaceId = searchRoomRequest.workspaceId,
                chatStatus = ChatStatusEnum.ALIVE,
                roomType = type,
                userId = userId
            ).orEmpty()

        when (type) {
            PERSONAL -> {
                val entity = chatRoomEntities.mapNotNull { it ->
                    val member = memberService.findById(
                        it.joinedUserInfo.first { it.userId != userId }.userId
                    )
                    if (member.name.contains(searchRoomRequest.word)) {
                        val chatRoom = chatRoomMapper.toDomain(it)
                        val updatedChatRoom = chatRoom.copy(
                            chatName = member.name,
                            chatRoomImg = member.picture
                        )
                        return@mapNotNull updatedChatRoom
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
                data = chatRoomEntities.map {
                    toResponse(chatRoomMapper.toDomain(it), userId)
                }
            )

        }

    }

}