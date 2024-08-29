package com.seugi.api.domain.profile.application.port.`in`

import com.seugi.api.domain.profile.adapter.`in`.request.EditSchIdNumRequest
import com.seugi.api.global.response.BaseResponse

interface EditSchIdNumUseCase {

    fun editSchIdNum(dto: EditSchIdNumRequest, workspaceId: String, id: Long): BaseResponse<Unit>

}