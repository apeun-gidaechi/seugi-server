package com.seugi.api.domain.chat.application.service.joined

import com.seugi.api.domain.chat.application.service.message.MessageService
import com.seugi.api.domain.chat.domain.chat.model.Type
import com.seugi.api.domain.chat.domain.enums.type.RoomType
import com.seugi.api.domain.chat.domain.joined.JoinedEntity
import com.seugi.api.domain.chat.domain.joined.JoinedRepository
import com.seugi.api.domain.chat.domain.joined.mapper.JoinedMapper
import com.seugi.api.domain.chat.domain.joined.model.Joined
import com.seugi.api.domain.chat.exception.ChatErrorCode
import com.seugi.api.domain.chat.presentation.joined.dto.request.AddJoinedRequest
import com.seugi.api.domain.chat.presentation.joined.dto.request.OutJoinedRequest
import com.seugi.api.domain.chat.presentation.joined.dto.request.TossMasterRequest
import com.seugi.api.domain.chat.presentation.websocket.dto.ChatMessageDto
import com.seugi.api.global.exception.CustomException
import com.seugi.api.global.response.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class JoinedServiceImpl(
    private val joinedRepository: JoinedRepository,
    private val joinedMapper: JoinedMapper,
    private val messageService: MessageService
) : JoinedService {

    @Transactional
    override fun joinUserJoined(chatRoomId: Long, joinedUserId: List<Long>, type: RoomType, roomAdmin: Long, workspaceId: String) {
        joinedRepository.save(
            joinedMapper.toEntity(chatRoomId, joinedUserId, type, roomAdmin, workspaceId)
        )
    }

    @Transactional(readOnly = true)
    override fun getUsersInfo(roomId: Long): BaseResponse<Joined> {
        return BaseResponse(
            status = HttpStatus.OK.value(),
            state = "J1",
            success = true,
            message = "맴버 정보 불러오기 성공",
            data = joinedMapper.toDomain(joinedRepository.findByChatRoomId(chatRoomId = roomId))
        )
    }

    @Transactional(readOnly = true)
    override fun findByJoinedUserId(workspaceId: String, userId: Long, roomType: RoomType): List<Joined> {
        return joinedRepository.findByWorkspaceIDAndJoinedUserIdContainsAndRoomType(workspaceId, userId, roomType).map { joinedMapper.toDomain(it) }
    }

    @Transactional(readOnly = true)
    override fun findByRoomId(roomId: Long): JoinedEntity {
        return joinedRepository.findByChatRoomId(roomId)
    }

    @Transactional
    override fun save(joinedEntity: JoinedEntity) {
        joinedRepository.save(joinedEntity)
    }

    @Transactional
    override fun addJoined(userId: Long, addJoinedRequest: AddJoinedRequest): BaseResponse<Joined> {

        val joinedEntity : JoinedEntity = joinedRepository.findByChatRoomId(addJoinedRequest.chatRoomId!!)

        joinedEntity.joinedUserId.addAll(
            addJoinedRequest.joinUserIds
        )

        joinedRepository.save(joinedEntity)

        messageService.sendAndSaveMessage(
            chatMessageDto = ChatMessageDto(
                type = Type.ENTER,
                roomId = addJoinedRequest.chatRoomId,
                eventList = addJoinedRequest.joinUserIds.toMutableList(),
                message = ""

            ),
            userId = userId
        )

        return BaseResponse(
            status = HttpStatus.OK.value(),
            success = true,
            state = "J1",
            message = "유저 채팅방에 추가 성공"

        )


    }

    @Transactional
    override fun outJoined(outJoinedRequest: OutJoinedRequest, userId: Long): BaseResponse<Unit> {

        val joined: JoinedEntity = joinedRepository.findByChatRoomId(outJoinedRequest.roomId!!)
        if (joined.roomAdmin == userId){
            joined.joinedUserId = (joined.joinedUserId - outJoinedRequest.outJoinedUsers.toSet()).toMutableSet()
        }

        joinedRepository.save(joined)

        messageService.sendAndSaveMessage(
            chatMessageDto = ChatMessageDto(
                type = Type.LEFT,
                roomId = outJoinedRequest.roomId,
                eventList = outJoinedRequest.outJoinedUsers.toMutableList(),
                message = ""
            ),
            userId = userId
        )

        return BaseResponse(
            status = HttpStatus.OK.value(),
            state = "J1",
            success = true,
            message = "맴버 내보내기 성공"
        )
    }

    @Transactional
    override fun tossMaster(userId: Long, tossMasterRequest: TossMasterRequest): BaseResponse<Unit> {
        val joined: JoinedEntity = joinedRepository.findByChatRoomId(tossMasterRequest.roomId)
        if (joined.roomAdmin == userId){
            joined.roomAdmin = tossMasterRequest.tossUserId
            joinedRepository.save(joined)
            return BaseResponse(
                status = HttpStatus.OK.value(),
                state = "J1",
                success = true,
                message = "방장 전달 성공"
            )
        }
        else throw CustomException(ChatErrorCode.NO_ACCESS_ROOM)
    }

}