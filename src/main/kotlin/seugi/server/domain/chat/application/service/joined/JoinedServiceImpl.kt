package seugi.server.domain.chat.application.service.joined

import org.springframework.stereotype.Service
import seugi.server.domain.chat.domain.joined.JoinedRepository
import seugi.server.domain.chat.domain.joined.mapper.JoinedMapper
import seugi.server.domain.chat.domain.joined.model.Joined

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

    override fun addJoined(userId: Long, joined: Joined) {
        joinedRepository.findById(joined.chatRoomId)
    }

}