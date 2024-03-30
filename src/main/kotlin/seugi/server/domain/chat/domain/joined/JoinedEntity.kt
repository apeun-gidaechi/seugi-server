package seugi.server.domain.chat.domain.joined

import jakarta.persistence.*
import org.springframework.data.mongodb.core.mapping.Document
import seugi.server.domain.chat.domain.room.ChatRoomEntity


@Document(collection = "join")
class JoinedEntity (
    @Id
    val chatRoomId: Long? = null,

    val joinedUserId : Long?  = null,
)