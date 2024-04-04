package seugi.server.domain.chat.application.service.joined

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import seugi.server.domain.chat.domain.joined.JoinedEntity
import seugi.server.domain.chat.domain.joined.JoinedRepository
import seugi.server.domain.chat.domain.joined.mapper.JoinedMapper
import seugi.server.domain.chat.domain.joined.model.Joined
import seugi.server.domain.chat.presentation.joined.dto.request.AddJoinedRequest
import seugi.server.global.response.BaseResponse

@Service
class JoinedServiceImpl(
    private val joinedRepository: JoinedRepository,
    private val joinedMapper: JoinedMapper
) : JoinedService {

    override fun joinUserJoined(chatRoomId: Long, joinedUserId: List<Long>) {
        joinedRepository.save(
            joinedMapper.toEntity(chatRoomId, joinedUserId)
        )
    }

    override fun findByJoinedUserId(userId: Long): List<Joined> {
        return joinedRepository.findByJoinedUserIdEquals(userId).map { joinedMapper.toDomain(it) }
    }

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

}