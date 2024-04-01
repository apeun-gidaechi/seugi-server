package seugi.server.domain.chat.application.service.joined

import org.springframework.stereotype.Service
import seugi.server.domain.chat.domain.joined.JoinedRepository
import seugi.server.domain.chat.domain.joined.mapper.JoinedMapper

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

}