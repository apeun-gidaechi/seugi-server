package com.seugi.api.domain.member.application.port.`in`

import com.seugi.api.domain.member.adapter.`in`.dto.RetrieveProfileDTO
import com.seugi.api.global.response.BaseResponse

interface RetrieveProfileUseCase {

    fun retrieveProfile(workspaceId: String, memberId: Long): BaseResponse<RetrieveProfileDTO>

}