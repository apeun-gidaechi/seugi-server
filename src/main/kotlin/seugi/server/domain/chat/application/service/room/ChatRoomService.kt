package seugi.server.domain.chat.application.service.room

import seugi.server.domain.chat.domain.room.model.Room
import seugi.server.domain.chat.presentation.room.dto.request.CreateRoomRequest
import seugi.server.global.response.BaseResponse


interface ChatRoomService {

    fun createChatRoom(createRoomRequest: CreateRoomRequest, userId:Long) : BaseResponse<Long>

    fun searchRooms(userId: Long) : BaseResponse<List<Room>>

}