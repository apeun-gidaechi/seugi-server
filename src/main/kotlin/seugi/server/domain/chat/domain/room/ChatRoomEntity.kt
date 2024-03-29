package seugi.server.domain.chat.domain.room

import jakarta.persistence.*
import org.aspectj.bridge.Message
import seugi.server.domain.chat.domain.chat.MessageEntity
import seugi.server.domain.chat.domain.joined.JoinedEntity
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
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    var chatStatus: ChatStatusEnum = ChatStatusEnum.ALIVE,

    @OneToMany(mappedBy = "chatRoom", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var messages: MutableList<MessageEntity> = emptyArray<MessageEntity>().toMutableList(),

    @OneToMany(mappedBy = "chatRoom", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var joined: MutableList<JoinedEntity>
)