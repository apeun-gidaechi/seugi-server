package seugi.server.domain.chat.domain.joined

import com.querydsl.core.annotations.QueryEntity
import jakarta.persistence.*
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection = "join")
@QueryEntity
class JoinedEntity (
    @Id
    val chatRoomId: Long? = null,

    var joinedUserId : MutableList<Long> = emptyArray<Long>().toMutableList(),
)