package com.seugi.api.domain.profile.adapter.`in`.controller

import com.seugi.api.domain.profile.adapter.`in`.response.RetrieveProfileDTO
import com.seugi.api.domain.profile.application.port.`in`.RetrieveProfileUseCase
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/member")
class RetrieveProfileController (
    private val retrieveMemberUseCase: RetrieveProfileUseCase
) {

    @GetMapping("/profile/load")
    fun retrieveProfile(workspaceId: String, memberId: Long): BaseResponse<RetrieveProfileDTO> {
        return retrieveMemberUseCase.retrieveProfile(workspaceId, memberId)
    }

}