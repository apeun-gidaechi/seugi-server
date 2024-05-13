package com.seugi.api.domain.member.adapter.`in`.controller

import com.seugi.api.domain.member.adapter.`in`.dto.EditProfileDTO
import com.seugi.api.domain.member.application.port.`in`.EditMemberUseCase
import com.seugi.api.global.common.annotation.GetAuthenticatedId
import com.seugi.api.global.response.BaseResponse
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/member/edit")
class EditMemberController (
    private val editMemberUseCase: EditMemberUseCase
) {

    @GetMapping("/picture")
    fun editPicture(url: String, @GetAuthenticatedId id: Long) : BaseResponse<Unit> {
        return editMemberUseCase.editPicture(url, id)
    }

    @PostMapping("/profile/{workspaceId}")
    fun editProfile(@RequestBody dto: EditProfileDTO,
                    @PathVariable workspaceId: String,
                    @GetAuthenticatedId id: Long
    ): BaseResponse<Unit> {
        return editMemberUseCase.editProfile(dto, workspaceId, id)
    }

}