package seugi.server.domain.chat.domain.chat.embeddable


data class Emoji(
    var emojiId : Int? = null,
    var userId: MutableSet<Long> = mutableSetOf()
)

data class MessageUserId(
    var userId: MutableSet<Long> = mutableSetOf()
)

data class MessageMember(
    var id: Long,
    var name: String,
    var email: String? = null,
    var birth: String? = null
)