package com.seugi.api.domain.member.application.port.`in`

import com.seugi.api.domain.member.adapter.`in`.dto.EditProfileDTO
import com.seugi.api.global.response.BaseResponse

interface EditProfileUseCase {

    fun editProfile(dto: EditProfileDTO, workspaceId: String, id: Long): BaseResponse<Unit>

}