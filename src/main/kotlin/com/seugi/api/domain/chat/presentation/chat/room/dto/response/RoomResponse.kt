package com.seugi.api.domain.chat.presentation.chat.room.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.seugi.api.domain.chat.domain.enums.status.ChatStatusEnum
import com.seugi.api.domain.chat.domain.enums.type.RoomType

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class RoomResponse(
    val id: String,
    val workspaceId: String,
    val type: RoomType,
    val roomAdmin: Long,
    val chatName: String,
    val chatRoomImg: String,
    val createdAt: String,
    val chatStatusEnum: ChatStatusEnum,
    val joinUserInfo: Set<UserInfoResponse>,
    val lastMessage: String,
    val lastMessageTimestamp: String,
    val notReadCnt: Int,
)