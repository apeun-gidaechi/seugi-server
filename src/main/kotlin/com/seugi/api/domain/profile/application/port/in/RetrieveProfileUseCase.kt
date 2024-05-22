package com.seugi.api.domain.profile.application.port.`in`

import com.seugi.api.domain.profile.adapter.`in`.response.RetrieveProfileResponse
import com.seugi.api.global.response.BaseResponse

interface RetrieveProfileUseCase {

    fun retrieveProfile(workspaceId: String, memberId: Long): BaseResponse<RetrieveProfileResponse>

}