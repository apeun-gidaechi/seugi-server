package seugi.server.domain.chat.domain.chat

import jakarta.persistence.*
import seugi.server.domain.chat.domain.chat.embeddable.Emoji
import seugi.server.domain.chat.domain.chat.embeddable.Read
import seugi.server.domain.chat.domain.chat.embeddable.UnRead
import seugi.server.domain.chat.domain.room.ChatRoomEntity
import seugi.server.domain.chat.domain.status.ChatStatusEnum
import java.time.LocalDateTime


@Entity
class MessageEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    var chatRoom: ChatRoomEntity,

    @Column(nullable = false)
    val writer:String,

    @Column(nullable = false)
    val message:String,

    @ElementCollection
    var emoji:MutableList<Emoji> = emptyArray<Emoji>().toMutableList(),

    @Column(nullable = false)
    val timestamp : LocalDateTime = LocalDateTime.now(),

    @ElementCollection
    var read: MutableList<Read> = emptyArray<Read>().toMutableList(),

    @ElementCollection
    var unRead: MutableList<UnRead> = emptyArray<UnRead>().toMutableList(),

    @Enumerated(value = EnumType.STRING)
    var messageStatus: ChatStatusEnum = ChatStatusEnum.ALIVE

)