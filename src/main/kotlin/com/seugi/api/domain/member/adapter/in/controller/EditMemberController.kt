package com.seugi.api.domain.member.adapter.`in`.controller

import com.seugi.api.domain.member.application.port.`in`.EditMemberUseCase
import com.seugi.api.global.common.annotation.GetAuthenticatedId
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/member/edit")
class EditMemberController (
    private val editMemberUseCase: EditMemberUseCase
) {

    @GetMapping("/picture")
    fun editPicture(url: String, @GetAuthenticatedId id: Long) : BaseResponse<Unit> {
        return editMemberUseCase.editPicture(url, id)
    }

}