package com.seugi.api.domain.chat.domain.room

import com.seugi.api.domain.chat.domain.enums.status.ChatStatusEnum
import com.seugi.api.domain.chat.domain.enums.type.RoomType
import jakarta.persistence.*
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime


@Document(collection = "chatRooms")
class ChatRoomEntity(
    @Id
    val id: ObjectId? = null,

    val workspaceID: String,

    val roomType: RoomType,

    var roomAdmin: Long,

    var chatName: String,

    var chatRoomImg: String = "",

    val createdAt: LocalDateTime = LocalDateTime.now(),

    var joinedUserId: Set<Long>,

    @Enumerated(EnumType.STRING)
    var chatStatus: ChatStatusEnum = ChatStatusEnum.ALIVE
)