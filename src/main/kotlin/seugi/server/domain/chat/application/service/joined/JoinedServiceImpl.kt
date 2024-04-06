package seugi.server.domain.chat.application.service.joined

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import seugi.server.domain.chat.domain.enums.type.RoomType
import seugi.server.domain.chat.domain.joined.JoinedEntity
import seugi.server.domain.chat.domain.joined.JoinedRepository
import seugi.server.domain.chat.domain.joined.mapper.JoinedMapper
import seugi.server.domain.chat.domain.joined.model.Joined
import seugi.server.domain.chat.presentation.joined.dto.request.AddJoinedRequest
import seugi.server.domain.chat.presentation.joined.dto.request.OutJoinedRequest
import seugi.server.global.response.BaseResponse

@Service
class JoinedServiceImpl(
    private val joinedRepository: JoinedRepository,
    private val joinedMapper: JoinedMapper
) : JoinedService {

    @Transactional
    override fun joinUserJoined(chatRoomId: Long, joinedUserId: List<Long>, type: RoomType, roomAdmin: Long) {
        joinedRepository.save(
            joinedMapper.toEntity(chatRoomId, joinedUserId, type, roomAdmin)
        )
    }

    @Transactional(readOnly = true)
    override fun findByJoinedUserId(userId: Long, roomType: RoomType): List<Joined> {
        return joinedRepository.findByJoinedUserIdEqualsAndRoomType(userId, roomType).map { joinedMapper.toDomain(it) }
    }

    @Transactional
    override fun addJoined(userId: Long, addJoinedRequest: AddJoinedRequest): BaseResponse<Joined> {

        val joinedEntity : JoinedEntity = joinedRepository.findByChatRoomId(addJoinedRequest.chatRoomId!!)

        joinedEntity.joinedUserId.addAll(
            addJoinedRequest.joinUserIds
        )

        return BaseResponse(
            status = HttpStatus.OK,
            success = true,
            state = "J1",
            message = "유저 채팅방에 추가 성공",
            data = joinedMapper.toDomain(joinedRepository.save(joinedEntity))

        )


    }

    @Transactional
    override fun outJoined(outJoinedRequest: OutJoinedRequest, userId: Long): BaseResponse<Unit> {

        val joined: JoinedEntity = joinedRepository.findByChatRoomId(outJoinedRequest.roomId!!)
        if (joined.roomAdmin == userId){
            joined.joinedUserId = (joined.joinedUserId - outJoinedRequest.outJoinedUsers.toSet()).toMutableSet()
        }
        joinedRepository.save(joined)

        return BaseResponse(
            status = HttpStatus.OK,
            state = "J1",
            success = true,
            message = "맴버 내보내기 성공"
        )
    }

}