package com.seugi.api.domain.profile.adapter.`in`.controller

import com.seugi.api.domain.profile.adapter.`in`.request.EditProfileDTO
import com.seugi.api.domain.profile.application.port.`in`.EditProfileUseCase
import com.seugi.api.global.common.annotation.GetAuthenticatedId
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/member")
class EditProfileController (
    private val editProfileUseCase: EditProfileUseCase
) {

    @PatchMapping("/profile/{workspaceId}")
    fun editProfile(@RequestBody dto: EditProfileDTO,
                    @PathVariable workspaceId: String,
                    @GetAuthenticatedId id: Long
    ): BaseResponse<Unit> {
        return editProfileUseCase.editProfile(dto, workspaceId, id)
    }

}