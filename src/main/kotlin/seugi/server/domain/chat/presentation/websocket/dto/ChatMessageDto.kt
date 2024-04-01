package seugi.server.domain.chat.presentation.websocket.dto

data class ChatMessageDto(
    var roomId : Long,
    var writer : String,
    var message : String
)
