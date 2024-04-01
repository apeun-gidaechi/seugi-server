package seugi.server.domain.chat.domain.joined.mapper

import org.springframework.stereotype.Component
import seugi.server.domain.chat.domain.joined.JoinedEntity
import seugi.server.domain.chat.domain.joined.model.Joined
import seugi.server.domain.chat.domain.room.ChatRoomEntity
import seugi.server.domain.chat.presentation.room.dto.request.CreateRoomRequest
import seugi.server.global.common.Mapper

@Component
class JoinedMapper : Mapper<Joined, JoinedEntity> {
    override fun toDomain(entity: JoinedEntity): Joined {
        return Joined(
            joinUserId = entity.joinedUserId,
            chatRoomId = entity.chatRoomId!!
        )
    }

    override fun toEntity(domain: Joined): JoinedEntity {
        return JoinedEntity(
            joinedUserId = domain.joinUserId,
            chatRoomId = domain.chatRoomId
        )
    }

    fun toEntity(chatRoomId : Long, joinedUserId : List<Long>) : JoinedEntity{
        return JoinedEntity(
            joinedUserId = joinedUserId.toMutableList(),
            chatRoomId = chatRoomId
        )
    }
}