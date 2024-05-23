package com.seugi.api.domain.chat.domain.joined

import com.seugi.api.domain.chat.domain.enums.type.RoomType
import jakarta.persistence.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection = "join")
class JoinedEntity(
    @Id
    val id: ObjectId? = null,

    val workspaceID: String,

    val chatRoomId: Long? = null,

    val roomType: RoomType,

    var roomAdmin: Long = -1,

    var joinedUserId: MutableSet<Long> = emptyArray<Long>().toMutableSet(),
)