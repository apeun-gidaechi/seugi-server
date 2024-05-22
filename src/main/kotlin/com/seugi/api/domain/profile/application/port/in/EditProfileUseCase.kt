package com.seugi.api.domain.profile.application.port.`in`

import com.seugi.api.domain.profile.adapter.`in`.request.EditProfileRequest
import com.seugi.api.global.response.BaseResponse

interface EditProfileUseCase {

    fun editProfile(dto: EditProfileRequest, workspaceId: String, id: Long): BaseResponse<Unit>

}