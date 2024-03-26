package seugi.server.domain.chat.domain.chat

import jakarta.persistence.*
import seugi.server.domain.chat.domain.chat.embeddable.emoji
import seugi.server.domain.chat.domain.room.ChatRoomEntity
import seugi.server.domain.chat.status.ChatStatusEnum
import java.time.LocalDateTime


@Entity
class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null

    @Column(nullable = false)
    val writer:String = ""

    @Column(nullable = false)
    val message:String = ""

    @ElementCollection
    var emoji:List<emoji> = emptyList()

    @Column(nullable = false)
    val timestamp : LocalDateTime = LocalDateTime.now()

    @Enumerated(value = EnumType.STRING)
    var messageStatus: ChatStatusEnum = ChatStatusEnum.ALIVE

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    var chatRoom: ChatRoomEntity? = null


}