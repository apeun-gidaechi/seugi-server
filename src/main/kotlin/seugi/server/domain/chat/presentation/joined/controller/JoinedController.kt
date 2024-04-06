package seugi.server.domain.chat.presentation.joined.controller

import org.hibernate.mapping.Join
import org.springframework.web.bind.annotation.*
import seugi.server.domain.chat.application.service.joined.JoinedService
import seugi.server.domain.chat.domain.joined.model.Joined
import seugi.server.domain.chat.presentation.joined.dto.request.AddJoinedRequest
import seugi.server.domain.chat.presentation.joined.dto.request.OutJoinedRequest
import seugi.server.global.common.annotation.GetAuthenticatedId
import seugi.server.global.response.BaseResponse

/**
 * 참가자 추가, 삭제, 확인
 */


@RestController
@RequestMapping("/joined")
class JoinedController(
    private val joinedService: JoinedService
) {

    //참가자 확인
    @GetMapping("/{roomId}")
    fun getUsersInfo(
        @PathVariable roomId: Long
        ): BaseResponse<Joined>{
        return joinedService.getUsersInfo(roomId)
    }

    //참가자 추가
    @PostMapping("/add")
    fun addJoined(
        @GetAuthenticatedId userId: Long,
        @RequestBody addJoinedRequest: AddJoinedRequest
    ): BaseResponse<Joined> {
        return joinedService.addJoined(
            userId = userId,
            addJoinedRequest = addJoinedRequest
        )
    }

    //참가자 삭제
    @PatchMapping("/out")
    fun outJoined(
        @GetAuthenticatedId userId: Long,
        @RequestBody outJoinedRequest: OutJoinedRequest
    ): BaseResponse<Unit>{
        return joinedService.outJoined(outJoinedRequest, userId)
    }

}