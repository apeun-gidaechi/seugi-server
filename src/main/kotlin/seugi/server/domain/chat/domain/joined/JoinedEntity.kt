package seugi.server.domain.chat.domain.joined

import com.querydsl.core.annotations.QueryEntity
import jakarta.persistence.*
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection = "join")
@QueryEntity
class JoinedEntity (
    @Id
    val id:ObjectId? = null,

    val chatRoomId: Long? = null,

    var joinedUserId : MutableSet<Long> = emptyArray<Long>().toMutableSet(),
)