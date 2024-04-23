package com.seugi.api.domain.chat.domain.room

import com.seugi.api.domain.chat.domain.room.model.Room

interface ChatRoomRepositoryCustom {
    fun searchRoom(chatRoomId: Long, input: String): Room?
}