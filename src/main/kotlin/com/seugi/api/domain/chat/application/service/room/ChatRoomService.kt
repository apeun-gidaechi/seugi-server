package com.seugi.api.domain.chat.application.service.room

import com.seugi.api.domain.chat.domain.enums.type.RoomType
import com.seugi.api.domain.chat.domain.room.model.Room
import com.seugi.api.domain.chat.presentation.room.dto.request.CreateRoomRequest
import com.seugi.api.global.response.BaseResponse


interface ChatRoomService {

    fun createChatRoom(createRoomRequest: CreateRoomRequest, userId:Long, type:RoomType) : BaseResponse<Long>

    fun getRooms(workspaceId: String, userId: Long, type: RoomType) : BaseResponse<List<Room>>

    fun leftRoom(userId: Long, roomId: Long) : BaseResponse<Unit>

}