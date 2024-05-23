package com.seugi.api.domain.chat.presentation.room.dto.response

import com.seugi.api.domain.chat.domain.enums.status.ChatStatusEnum
import com.seugi.api.domain.chat.domain.enums.type.RoomType
import com.seugi.api.domain.member.adapter.`in`.dto.res.RetrieveMemberResponse

data class RoomResponse(
    val id: String,
    val workspaceID: String,
    val type: RoomType,
    val roomAdmin: Long,
    val chatName: String,
    val chatRoomImg: String,
    val createdAt: String,
    val chatStatusEnum: ChatStatusEnum,
    val joinUserId: Set<RetrieveMemberResponse>
)