package com.seugi.api.domain.member.adapter.`in`.controller

import com.seugi.api.domain.member.adapter.`in`.dto.req.LogoutMemberRequest
import com.seugi.api.domain.member.application.port.`in`.LogoutMemberUseCase
import com.seugi.api.global.common.annotation.GetAuthenticatedId
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/member")
class LogoutMemberController(
    private val logoutMemberUseCase: LogoutMemberUseCase,
) {

    @PostMapping("/logout")
    fun logoutMember(
        @GetAuthenticatedId userId: Long,
        @RequestBody logoutMemberRequest: LogoutMemberRequest,
    ): BaseResponse<Unit> {
        return logoutMemberUseCase.logoutMember(userId, logoutMemberRequest.fcmToken)
    }

}