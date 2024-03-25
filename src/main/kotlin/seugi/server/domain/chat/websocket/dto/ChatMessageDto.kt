package seugi.server.domain.chat.websocket.dto

data class ChatMessageDto(
    var roomId : String,
    var writer : String,
    var message : String,
    var emoji : Long,
    var time  : String
)
