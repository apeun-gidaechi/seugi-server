package seugi.server.domain.chat.application.service.room

import seugi.server.domain.chat.domain.enums.type.RoomType
import seugi.server.domain.chat.domain.room.model.Room
import seugi.server.domain.chat.presentation.room.dto.request.CreateRoomRequest
import seugi.server.global.response.BaseResponse


interface ChatRoomService {

    fun createChatRoom(createRoomRequest: CreateRoomRequest, userId:Long, type:RoomType) : BaseResponse<Long>

    fun getRooms(userId: Long, type: RoomType) : BaseResponse<List<Room>>

    fun leftRoom(userId: Long, roomId: Long) : BaseResponse<Unit>

}