package com.seugi.api.domain.profile.adapter.`in`.controller

import com.seugi.api.domain.profile.adapter.`in`.response.RetrieveProfileResponse
import com.seugi.api.domain.profile.application.port.`in`.RetrieveProfileUseCase
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

    @GetMapping("/retrieve")
    fun retrieveProfile(
        @RequestParam workspaceId: String,
        @RequestParam memberId: Long
    ): BaseResponse<RetrieveProfileResponse> {
        return retrieveMemberUseCase.retrieveProfile(workspaceId, memberId)
    }

}