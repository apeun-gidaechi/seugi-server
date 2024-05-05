package com.seugi.api.domain.member.port.`in`

import com.seugi.api.domain.member.adapter.`in`.dto.EditProfileDTO
import com.seugi.api.global.response.BaseResponse

interface EditMemberUseCase {

    fun editPicture(url: String, id: Long): BaseResponse<Unit>

    fun editProfile(dto: EditProfileDTO, workspaceId: String, id: Long)

}