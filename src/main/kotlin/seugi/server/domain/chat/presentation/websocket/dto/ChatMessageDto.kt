package seugi.server.domain.chat.presentation.websocket.dto

data class ChatMessageDto(
    var roomId : Long? = null,
    var message : String? = null
)
