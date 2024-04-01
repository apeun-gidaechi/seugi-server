package seugi.server.domain.chat.domain.room

import jakarta.persistence.*
import org.hibernate.annotations.UpdateTimestamp
import seugi.server.domain.chat.domain.status.ChatStatusEnum
import java.time.LocalDateTime


@Entity
class ChatRoomEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    var id: Long? = null,

    @Column(nullable = false)
    var chatName: String,

    @Column(nullable = false)
    var containUserCnt : Long,

    @Column(nullable = false)
    var chatRoomImg : String? = null,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    val lastModified : LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    var chatStatus: ChatStatusEnum = ChatStatusEnum.ALIVE
)