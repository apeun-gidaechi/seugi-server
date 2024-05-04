package com.seugi.api.domain.chat.domain.room

import jakarta.persistence.*
import com.seugi.api.domain.chat.domain.enums.status.ChatStatusEnum
import com.seugi.api.domain.chat.domain.enums.type.RoomType
import java.time.LocalDateTime


@Entity
class ChatRoomEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    val id: Long? = null,

    @Enumerated(EnumType.ORDINAL)
    val roomType: RoomType,

    @Column(nullable = false)
    var chatName: String,

    @Column(nullable = false)
    var containUserCnt : Long,

    @Column(nullable = false)
    var chatRoomImg : String = "",

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    val lastModified : LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    var chatStatus: ChatStatusEnum = ChatStatusEnum.ALIVE
)