package com.seugi.api.domain.profile.adapter.`in`.controller

import com.seugi.api.domain.member.domain.model.Member
import com.seugi.api.domain.profile.adapter.`in`.request.EditProfileRequest
import com.seugi.api.domain.profile.application.port.`in`.EditProfileUseCase
import com.seugi.api.global.common.annotation.GetResolvedMember
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/profile")
class EditProfileController (
    private val editProfileUseCase: EditProfileUseCase
) {

    @PatchMapping("/{workspaceId}")
    fun editProfile(@RequestBody dto: EditProfileRequest,
                    @PathVariable workspaceId: String,
                    @GetResolvedMember model: Member
    ): BaseResponse<Unit> {
        return editProfileUseCase.editProfile(dto, workspaceId, model.id)
    }

}