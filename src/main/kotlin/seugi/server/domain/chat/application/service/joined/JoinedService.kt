package seugi.server.domain.chat.application.service.joined

import seugi.server.domain.chat.domain.enums.type.RoomType
import seugi.server.domain.chat.domain.joined.JoinedEntity
import seugi.server.domain.chat.domain.joined.model.Joined
import seugi.server.domain.chat.presentation.joined.dto.request.AddJoinedRequest
import seugi.server.domain.chat.presentation.joined.dto.request.OutJoinedRequest
import seugi.server.global.response.BaseResponse

interface JoinedService {
    fun joinUserJoined(chatRoomId : Long , joinedUserId: List<Long>, type:RoomType, roomAdmin:Long)
    fun getUsersInfo(roomId: Long): BaseResponse<Joined>
    fun findByJoinedUserId(userId: Long, roomType: RoomType): List<Joined>
    fun findByRoomId(roomId: Long) : JoinedEntity
    fun save(joinedEntity: JoinedEntity)
    fun addJoined(userId: Long, addJoinedRequest: AddJoinedRequest): BaseResponse<Joined>
    fun outJoined(outJoinedRequest: OutJoinedRequest, userId: Long): BaseResponse<Unit>
}