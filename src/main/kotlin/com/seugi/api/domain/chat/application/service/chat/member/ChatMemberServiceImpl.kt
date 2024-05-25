package com.seugi.api.domain.chat.application.service.chat.member

import com.seugi.api.domain.chat.application.service.message.MessageService
import com.seugi.api.domain.chat.domain.chat.model.Type
import com.seugi.api.domain.chat.domain.enums.type.EventType
import com.seugi.api.domain.chat.domain.room.ChatRoomRepository
import com.seugi.api.domain.chat.exception.ChatErrorCode
import com.seugi.api.domain.chat.presentation.chat.member.dto.request.ChatMemberEventRequest
import com.seugi.api.domain.chat.presentation.websocket.dto.ChatMessageDto
import com.seugi.api.global.exception.CustomException
import com.seugi.api.global.response.BaseResponse
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatMemberServiceImpl(
    private val chatRoomRepository: ChatRoomRepository,
    private val messageService: MessageService
) : ChatMemberService {

    @Transactional
    override fun performCommonEventChangeTasks(
        userId: Long,
        chatMemberEventRequest: ChatMemberEventRequest,
        eventType: EventType
    ): BaseResponse<Unit> {
        //미리 채팅방 이름이 24글자가 아닌지 확인하여 오류방지
        if (chatMemberEventRequest.chatRoomId?.length != 24) throw CustomException(ChatErrorCode.CHAT_ROOM_ID_ERROR)
        return when (eventType) {
            EventType.ADD -> addUsers(userId, chatMemberEventRequest)
            EventType.KICK -> kickUsers(userId, chatMemberEventRequest)
            EventType.TOSS_ADMIN -> tossAdmin(userId, chatMemberEventRequest)
        }
    }

    private fun sendMessage(type: Type, roomId: String, eventList: Set<Long>, userId: Long) {
        messageService.sendAndSaveMessage(
            chatMessageDto = ChatMessageDto(
                type = type,
                roomId = roomId,
                eventList = eventList,
                message = ""
            ),
            userId = userId
        )
    }

    private fun addUsers(userId: Long, chatMemberEventRequest: ChatMemberEventRequest): BaseResponse<Unit> {

        val chatRoomEntity = chatRoomRepository.findById(ObjectId(chatMemberEventRequest.chatRoomId))
            .orElseThrow { CustomException(ChatErrorCode.CHAT_ROOM_NOT_FOUND) }

        chatRoomEntity.joinedUserId += chatMemberEventRequest.chatMemberUsers

        chatRoomRepository.save(chatRoomEntity)

        sendMessage(
            type = Type.ENTER,
            roomId = chatMemberEventRequest.chatRoomId!!,
            eventList = chatMemberEventRequest.chatMemberUsers,
            userId = userId
        )

        return BaseResponse(
            status = HttpStatus.OK.value(),
            success = true,
            state = "OK",
            message = "유저 채팅방에 추가 성공"
        )

    }

    private fun kickUsers(userId: Long, chatMemberEventRequest: ChatMemberEventRequest): BaseResponse<Unit> {

        val chatRoomEntity = chatRoomRepository.findById(ObjectId(chatMemberEventRequest.chatRoomId))
            .orElseThrow { CustomException(ChatErrorCode.CHAT_ROOM_NOT_FOUND) }


        if (chatRoomEntity.roomAdmin == userId) {
            chatRoomEntity.joinedUserId -= chatMemberEventRequest.chatMemberUsers
        }

        chatRoomRepository.save(chatRoomEntity)

        sendMessage(
            type = Type.LEFT,
            roomId = chatMemberEventRequest.chatRoomId!!,
            eventList = chatMemberEventRequest.chatMemberUsers,
            userId = userId
        )

        return BaseResponse(
            status = HttpStatus.OK.value(),
            state = "OK",
            success = true,
            message = "맴버 내보내기 성공"
        )
    }

    private fun tossAdmin(userId: Long, chatMemberEventRequest: ChatMemberEventRequest): BaseResponse<Unit> {

        if (chatMemberEventRequest.chatMemberUsers.size != 1) throw CustomException(ChatErrorCode.CHAT_TOSS_ADMIN_ERROR)

        val chatRoomEntity = chatRoomRepository.findById(ObjectId(chatMemberEventRequest.chatRoomId))
            .orElseThrow { CustomException(ChatErrorCode.CHAT_ROOM_NOT_FOUND) }

        if (chatRoomEntity.roomAdmin != userId) throw CustomException(ChatErrorCode.NO_ACCESS_ROOM)

        chatRoomEntity.roomAdmin = chatMemberEventRequest.chatMemberUsers.first()

        chatRoomRepository.save(chatRoomEntity)

        sendMessage(
            type = Type.TOSS_ADMIN,
            roomId = chatMemberEventRequest.chatRoomId!!,
            eventList = chatMemberEventRequest.chatMemberUsers,
            userId = userId
        )

        return BaseResponse(
            status = HttpStatus.OK.value(),
            state = "OK",
            success = true,
            message = "방장 전달 성공"
        )

    }

}