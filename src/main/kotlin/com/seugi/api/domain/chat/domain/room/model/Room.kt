package com.seugi.api.domain.chat.domain.room.model

import com.seugi.api.domain.chat.domain.enums.status.ChatStatusEnum
import com.seugi.api.domain.chat.domain.enums.type.RoomType

data class Room(
    val id: Long? = null,
    val type: RoomType,
    val chatName: String,
    val containUserCnt : Long,
    val createdAt: String? = null,
    val chatStatusEnum: ChatStatusEnum? = null
)