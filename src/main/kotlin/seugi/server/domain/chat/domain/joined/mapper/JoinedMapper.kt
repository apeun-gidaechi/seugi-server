package seugi.server.domain.chat.domain.joined.mapper

import org.springframework.stereotype.Component
import seugi.server.domain.chat.domain.joined.JoinedEntity
import seugi.server.domain.chat.domain.joined.model.Joined
import seugi.server.domain.chat.domain.room.ChatRoomEntity
import seugi.server.global.common.Mapper

@Component
class JoinedMapper : Mapper<Joined, JoinedEntity> {
    override fun toDomain(entity: JoinedEntity): Joined {
        return Joined(
            joinUserId = entity.joinedUserId!!,
            chatRoomId = entity.chatRoom?.id!!
        )
    }

    private var chatRoomEntity : ChatRoomEntity? = null

    override fun toEntity(domain: Joined): JoinedEntity {
        return JoinedEntity(
            joinedUserId = domain.joinUserId,
            chatRoom = this.chatRoomEntity
        )
    }

    private fun setChatRoomEntity(chatRoomEntity: ChatRoomEntity){
        this.chatRoomEntity = chatRoomEntity
    }

}