package seugi.server.domain.chat.application.service.joined

import seugi.server.domain.chat.domain.joined.model.Joined

interface JoinedService {
    fun joinUserJoined(chatRoomId : Long , joinedUserId: List<Long>)
    fun findByJoinedUserId(userId: Long) : List<Joined>
    fun addJoined(userId: Long, joined: Joined)
}