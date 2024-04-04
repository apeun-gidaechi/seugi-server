package seugi.server.domain.chat.presentation.joined.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import seugi.server.domain.chat.application.service.joined.JoinedService
import seugi.server.domain.chat.domain.joined.model.Joined
import seugi.server.domain.chat.presentation.joined.dto.request.AddJoinedRequest
import seugi.server.global.common.annotation.GetAuthenticatedId
import seugi.server.global.response.BaseResponse

/**
 * 참가자 추가, 삭제
 */


@RestController
@RequestMapping("/joined")
class JoinedController(
    private val joinedService: JoinedService
) {

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

}