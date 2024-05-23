package com.seugi.api.domain.chat.domain.chat.embeddable

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonSerialize


@JsonSerialize(using = EmojiSerializer::class)
data class Emoji(
    val emojiId: Int? = null,
    val userId: MutableSet<Long> = mutableSetOf()
)

data class AddEmoji(
    val messageId: String? = null,
    val roomId: Long? = null,
    val emojiId: Int? = null,
    val userId: MutableSet<Long> = mutableSetOf()
)

data class MessageUserId(
    val userId: MutableSet<Long> = mutableSetOf()
)

data class DeleteMessage(
    val roomId: Long? = null,
    val messageId: String? = null
)

data class MessageMember(
    val id: Long
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