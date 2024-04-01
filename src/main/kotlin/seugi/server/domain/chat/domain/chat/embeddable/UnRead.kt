package seugi.server.domain.chat.domain.chat.embeddable

import jakarta.persistence.Embeddable

@Embeddable
class UnRead(
    var userId: Long
)