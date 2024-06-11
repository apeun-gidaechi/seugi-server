package com.seugi.api.domain.member.adapter.`in`.controller

import com.seugi.api.domain.member.application.port.`in`.RemoveMemberUseCase
import com.seugi.api.global.common.annotation.GetAuthenticatedId
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/member")
class RemoveMemberController(
    private val removeMemberUseCase: RemoveMemberUseCase
) {

    @DeleteMapping("/remove")
    fun removeMember(@GetAuthenticatedId id: Long): BaseResponse<Unit> {
        return removeMemberUseCase.removeMember(id)
    }

}