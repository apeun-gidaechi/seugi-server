package com.seugi.api.domain.chat.domain.joined

import jakarta.persistence.*
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import com.seugi.api.domain.chat.domain.enums.type.RoomType


@Document(collection = "join")
class JoinedEntity (
    @Id
    val id:ObjectId? = null,

    val workspaceID: String,

    val chatRoomId: Long? = null,

    val roomType: RoomType,

    var roomAdmin: Long = -1,

    var joinedUserId : MutableSet<Long> = emptyArray<Long>().toMutableSet(),
)