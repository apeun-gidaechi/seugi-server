package seugi.server.domain.chat.domain.chat.embeddable

import jakarta.persistence.Embeddable
import java.time.LocalDateTime

@Embeddable
class Read(
    var userId: Long,
    var timestamp: LocalDateTime = LocalDateTime.now()
)