package seugi.server.domain.chat.domain.chat.embeddable


data class Emoji(
    var emojiId : Int? = null
)

data class EmojiUserId(
    var userId: MutableSet<Long> = mutableSetOf()
)