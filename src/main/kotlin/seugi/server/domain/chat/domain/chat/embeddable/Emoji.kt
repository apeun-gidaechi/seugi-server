package seugi.server.domain.chat.domain.chat.embeddable

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
class Emoji {
    @Column(name = "emoji_id")
    var emojiId: Long? = null
    @Column(name = "emoji_set_user")
    var emojiUserId: Long? = null
}