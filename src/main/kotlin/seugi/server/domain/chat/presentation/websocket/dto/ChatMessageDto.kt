package seugi.server.domain.chat.presentation.websocket.dto

data class ChatMessageDto(
    var roomId : Long? = null,
    var userId : Long? = null,
    var writer : String? = null,
    var message : String? = null,

)
