package com.seugi.api.domain.member.adapter.`in`.controller

import com.seugi.api.domain.member.adapter.`in`.dto.res.RetrieveMemberResponse
import com.seugi.api.domain.member.application.port.`in`.RetrieveMemberUseCase
import com.seugi.api.global.common.annotation.GetAuthenticatedId
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/member")
class RetrieveMemberController (
    private val retrieveMemberUseCase: RetrieveMemberUseCase
) {

    @GetMapping("/myInfo")
    fun getMyInfo(@GetAuthenticatedId memberId: Long): BaseResponse<RetrieveMemberResponse> {
        return retrieveMemberUseCase.retrieveMember(memberId)
    }

}