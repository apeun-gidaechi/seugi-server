package com.seugi.api.domain.profile.adapter.`in`.controller

import com.seugi.api.domain.profile.adapter.`in`.request.EditSchIdNumRequest
import com.seugi.api.domain.profile.application.port.`in`.EditSchIdNumUseCase
import com.seugi.api.global.common.annotation.GetAuthenticatedId
import com.seugi.api.global.response.BaseResponse
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/profile")
class EditSchoolIdNumController (
    private val editSchIdNumUseCase: EditSchIdNumUseCase
) {

    @PatchMapping("/schidnum/{workspaceId}")
    fun editSchIdNum(
        @RequestBody dto: EditSchIdNumRequest,
        @PathVariable workspaceId: String,
        @GetAuthenticatedId id: Long
    ): BaseResponse<Unit> {
        return editSchIdNumUseCase.editSchIdNum(dto, workspaceId, id)
    }

}