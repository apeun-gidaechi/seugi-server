//package com.seugi.api.domain.chat.presentation.joined.controller
//
//import com.seugi.api.domain.chat.application.service.joined.JoinedService
//import com.seugi.api.domain.chat.domain.joined.model.Joined
//import com.seugi.api.domain.chat.presentation.joined.dto.request.AddJoinedRequest
//import com.seugi.api.domain.chat.presentation.joined.dto.request.OutJoinedRequest
//import com.seugi.api.domain.chat.presentation.joined.dto.request.TossMasterRequest
//import com.seugi.api.global.common.annotation.GetAuthenticatedId
//import com.seugi.api.global.response.BaseResponse
//import org.springframework.web.bind.annotation.*
//
///**
// * 참가자 추가, 삭제, 확인
// */
//
//
//@RestController
//@RequestMapping("/joined")
//class JoinedController(
//    private val joinedService: JoinedService
//) {
//
//    //참가자 확인
//    @GetMapping("/{roomId}")
//    fun getUsersInfo(
//        @PathVariable roomId: Long
//    ): BaseResponse<Joined> {
//        return joinedService.getUsersInfo(roomId)
//    }
//
//    //참가자 추가
//    @PostMapping("/add")
//    fun addJoined(
//        @GetAuthenticatedId userId: Long,
//        @RequestBody addJoinedRequest: AddJoinedRequest
//    ): BaseResponse<Joined> {
//        return joinedService.addJoined(
//            userId = userId,
//            addJoinedRequest = addJoinedRequest
//        )
//    }
//
//    //참가자 삭제
//    @PatchMapping("/out")
//    fun outJoined(
//        @GetAuthenticatedId userId: Long,
//        @RequestBody outJoinedRequest: OutJoinedRequest
//    ): BaseResponse<Unit> {
//        return joinedService.outJoined(outJoinedRequest, userId)
//    }
//
//    // 방장 위임
//    @PatchMapping("/toss/master")
//    fun tossMaster(
//        @GetAuthenticatedId userId: Long,
//        @RequestBody toosMaster: TossMasterRequest
//    ): BaseResponse<Unit> {
//        return joinedService.tossMaster(userId, toosMaster)
//    }
//}