package seugi.server.domain.chat.presentation.websocket.dto

import seugi.server.domain.chat.domain.chat.embeddable.MessageUserId
import seugi.server.domain.chat.domain.chat.model.Type

data class ChatMessageDto(
    var type : Type? = Type.MESSAGE,
    var roomId : Long? = null,
    var message : String? = null,
    var mention : List<MessageUserId>? = emptyList(),
    var mentionAll : Boolean = false,
)
