package seugi.server.domain.chat.application.service.joined

interface JoinedService {
    fun joinUserJoined(chatRoomId : Long , joinedUserId: List<Long>)
}