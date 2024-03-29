package seugi.server.domain.chat.domain.joined.mapper

import seugi.server.domain.chat.domain.joined.JoinedEntity
import seugi.server.domain.chat.domain.joined.model.Joined
import seugi.server.domain.chat.domain.room.ChatRoomEntity
import seugi.server.domain.chat.domain.room.model.Room
import seugi.server.global.common.Mapper

class JoinedMapper : Mapper<Joined, JoinedEntity> {
    override fun toDomain(entity: JoinedEntity): Joined {
        return Joined(
            id = entity.id!!,
            chatRoomId = entity.chatRoom?.id!!
        )
    }

    private var chatRoomEntity : ChatRoomEntity? = null

    override fun toEntity(domain: Joined): JoinedEntity {
        return JoinedEntity(
            id = domain.id,
            chatRoom = this.chatRoomEntity
        )
    }

    private fun setChatRoomEntity(chatRoomEntity: ChatRoomEntity){
        this.chatRoomEntity = chatRoomEntity
    }

}