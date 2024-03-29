package seugi.server.domain.chat.domain.joined

import jakarta.persistence.*
import seugi.server.domain.chat.domain.room.ChatRoomEntity


@Entity
class JoinedEntity (
    @Id
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    var chatRoom: ChatRoomEntity? = null
)