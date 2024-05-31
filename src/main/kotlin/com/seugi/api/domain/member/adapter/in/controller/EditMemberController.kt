package com.seugi.api.domain.member.adapter.`in`.controller

import com.seugi.api.domain.member.adapter.`in`.dto.req.EditMemberRequest
import com.seugi.api.domain.member.application.port.`in`.EditMemberUseCase
import com.seugi.api.global.common.annotation.GetAuthenticatedId
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/member")
class EditMemberController (
    private val editMemberUseCase: EditMemberUseCase
) {

    @PatchMapping("/edit")
    fun editMember(@RequestBody dto: EditMemberRequest, @GetAuthenticatedId id: Long) : BaseResponse<Unit> {
        return editMemberUseCase.editMember(dto, id)
    }

}