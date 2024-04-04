package seugi.server.domain.chat.application.service.joined

import seugi.server.domain.chat.domain.enums.type.RoomType
import seugi.server.domain.chat.domain.joined.model.Joined
import seugi.server.domain.chat.presentation.joined.dto.request.AddJoinedRequest
import seugi.server.global.response.BaseResponse

interface JoinedService {
    fun joinUserJoined(chatRoomId : Long , joinedUserId: List<Long>)
    fun findByJoinedUserId(userId: Long, roomType: RoomType) : List<Joined>
    fun addJoined(userId: Long, addJoinedRequest: AddJoinedRequest): BaseResponse<Joined>
}