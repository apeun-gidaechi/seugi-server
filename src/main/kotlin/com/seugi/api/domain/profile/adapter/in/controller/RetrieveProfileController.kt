package com.seugi.api.domain.profile.adapter.`in`.controller

import com.seugi.api.domain.member.domain.model.Member
import com.seugi.api.domain.profile.adapter.`in`.response.RetrieveProfileResponse
import com.seugi.api.domain.profile.application.port.`in`.RetrieveProfileUseCase
import com.seugi.api.global.common.annotation.GetResolvedMember
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/profile")
class RetrieveProfileController (
    private val retrieveMemberUseCase: RetrieveProfileUseCase
) {

    @GetMapping("/me")
    fun getMyProfile(
        @RequestParam workspaceId: String,
        @GetResolvedMember model: Member
    ) : BaseResponse<RetrieveProfileResponse> {
        return retrieveMemberUseCase.retrieveProfile(workspaceId, model.id)
    }

    @GetMapping("/others")
    fun getOtherProfile(
        @RequestParam workspaceId: String,
        @RequestParam memberId: Long
    ): BaseResponse<RetrieveProfileResponse> {
        return retrieveMemberUseCase.retrieveProfile(workspaceId, memberId)
    }

}