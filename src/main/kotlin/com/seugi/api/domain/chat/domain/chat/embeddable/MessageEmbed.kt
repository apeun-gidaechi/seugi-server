package com.seugi.api.domain.chat.domain.chat.embeddable

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonSerialize


@JsonSerialize(using = EmojiSerializer::class)
data class Emoji(
    var emojiId : Int? = null,
    var userId: MutableSet<Long> = mutableSetOf()
)

data class AddEmoji(
    var roomId: Long? = null,
    var emojiId : Int? = null,
    var userId: MutableSet<Long> = mutableSetOf()
)

data class MessageUserId(
    var userId: MutableSet<Long> = mutableSetOf()
)

data class MessageMember(
    var id: Long,
    var name: String
)

private class EmojiSerializer : JsonSerializer<Emoji>() {
    override fun serialize(emoji: Emoji, gen: JsonGenerator, serializers: SerializerProvider) {
        if (emoji.userId.isNotEmpty()) {
            gen.writeStartObject()
            gen.writeNumberField("emojiId", emoji.emojiId!!)
            gen.writeArrayFieldStart("userId")
            for (id in emoji.userId) {
                gen.writeNumber(id)
            }
            gen.writeEndArray()
            gen.writeEndObject()
        }
    }
}