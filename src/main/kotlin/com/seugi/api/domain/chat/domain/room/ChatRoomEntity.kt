package com.seugi.api.domain.chat.domain.room

import com.seugi.api.domain.chat.domain.enums.status.ChatStatusEnum
import com.seugi.api.domain.chat.domain.enums.type.RoomType
import com.seugi.api.domain.chat.domain.room.model.JoinUserInfo
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime


@Document(collection = "chatRooms")
class ChatRoomEntity(
    @Id
    val id: ObjectId? = null,

    val workspaceId: String,

    val roomType: RoomType,

    var roomAdmin: Long,

    var chatName: String,

    var chatRoomImg: String = "",

    val createdAt: LocalDateTime,

    var joinedUserInfo: Set<JoinUserInfo>,

    @Enumerated(EnumType.STRING)
    var chatStatus: ChatStatusEnum = ChatStatusEnum.ALIVE,
)