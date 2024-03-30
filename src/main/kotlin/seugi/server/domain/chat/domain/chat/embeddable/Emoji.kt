package seugi.server.domain.chat.domain.chat.embeddable

import jakarta.persistence.Embeddable

@Embeddable
class Emoji(
    var userId: Long,
    var emojiId: Long
)
