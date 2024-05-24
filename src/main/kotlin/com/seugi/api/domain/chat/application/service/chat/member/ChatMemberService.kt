//package com.seugi.api.domain.chat.application.service.member
//
//import com.seugi.api.domain.chat.domain.enums.type.RoomType
//import com.seugi.api.domain.chat.domain.member.JoinedEntity
//import com.seugi.api.domain.chat.domain.member.model.Joined
//import com.seugi.api.domain.chat.presentation.member.dto.request.AddJoinedRequest
//import com.seugi.api.domain.chat.presentation.member.dto.request.OutJoinedRequest
//import com.seugi.api.domain.chat.presentation.member.dto.request.TossMasterRequest
//import com.seugi.api.global.response.BaseResponse
//
//interface JoinedService {
//    fun joinUserJoined(chatRoomId: Long, joinedUserId: List<Long>, type: RoomType, roomAdmin: Long, workspaceId: String)
//    fun getUsersInfo(roomId: Long): BaseResponse<Joined>
//    fun findByJoinedUserId(workspaceId: String, userId: Long, roomType: RoomType): List<Joined>
//    fun findByRoomId(roomId: Long): JoinedEntity
//    fun save(joinedEntity: JoinedEntity)
//    fun addJoined(userId: Long, addJoinedRequest: AddJoinedRequest): BaseResponse<Joined>
//    fun outJoined(outJoinedRequest: OutJoinedRequest, userId: Long): BaseResponse<Unit>
//    fun tossMaster(userId: Long, tossMasterRequest: TossMasterRequest): BaseResponse<Unit>
//}