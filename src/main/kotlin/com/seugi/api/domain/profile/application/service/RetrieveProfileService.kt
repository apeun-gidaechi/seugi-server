package com.seugi.api.domain.profile.application.service

import com.seugi.api.domain.profile.adapter.`in`.response.RetrieveProfileResponse
import com.seugi.api.domain.profile.application.port.`in`.RetrieveProfileUseCase
import com.seugi.api.domain.profile.application.port.out.LoadProfilePort
import com.seugi.api.global.response.BaseResponse
import org.springframework.stereotype.Service

@Service
class RetrieveProfileService (
    private val loadProfilePort: LoadProfilePort
): RetrieveProfileUseCase {

    override fun retrieveProfile(workspaceId: String, memberId: Long): BaseResponse<RetrieveProfileResponse> {
        val profile = loadProfilePort.loadProfile(memberId, workspaceId)

        return BaseResponse (
            message = "프로필 불러오기 성공",
            data = RetrieveProfileResponse(profile)
        )

    }

}