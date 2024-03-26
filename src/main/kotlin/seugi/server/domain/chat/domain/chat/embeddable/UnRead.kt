package seugi.server.domain.chat.domain.chat.embeddable

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class UnRead {
    @Column(name = "unread_user_id")
    var unreadUserId: Long? = null
}