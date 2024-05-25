package com.seugi.api.domain.chat.application.service.chat.room

import com.seugi.api.domain.chat.domain.enums.type.RoomType
import com.seugi.api.domain.chat.domain.room.model.Room
import com.seugi.api.domain.chat.presentation.chat.room.dto.request.CreateRoomRequest
import com.seugi.api.domain.chat.presentation.chat.room.dto.request.SearchRoomRequest
import com.seugi.api.global.response.BaseResponse


interface ChatRoomService {

    fun createChatRoom(createRoomRequest: CreateRoomRequest, userId: Long, type: RoomType): BaseResponse<String>

    fun getRoom(roomId: String, userId: Long): BaseResponse<Room>

    fun getRooms(workspaceId: String, userId: Long, type: RoomType): BaseResponse<List<Room>>

    fun leftRoom(userId: Long, roomId: String): BaseResponse<Unit>

    fun searchRoomNameIn(searchRoomRequest: SearchRoomRequest, type: RoomType, userId: Long): BaseResponse<List<Room>>

}