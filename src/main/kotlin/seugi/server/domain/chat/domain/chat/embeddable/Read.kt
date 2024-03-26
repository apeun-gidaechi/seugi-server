package seugi.server.domain.chat.domain.chat.embeddable

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class Read {
    @Column(name = "read_user_id")
    var userid : Long? = null
}