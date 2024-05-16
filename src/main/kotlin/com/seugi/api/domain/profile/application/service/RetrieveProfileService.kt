package com.seugi.api.domain.profile.application.service

import com.seugi.api.domain.profile.adapter.`in`.response.RetrieveProfileDTO
import com.seugi.api.domain.profile.application.port.`in`.RetrieveProfileUseCase
import com.seugi.api.domain.profile.application.port.out.LoadProfilePort
import com.seugi.api.global.response.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class RetrieveProfileService (
    private val loadProfilePort: LoadProfilePort
): RetrieveProfileUseCase {

    override fun retrieveProfile(workspaceId: String, memberId: Long): BaseResponse<RetrieveProfileDTO> {
        val entity = loadProfilePort.loadProfile(memberId, workspaceId)

        val profile = RetrieveProfileDTO (
            workspaceId = entity.workspaceId,
            status = entity.status,
            nick = entity.nick,
            spot = entity.spot,
            belong = entity.belong,
            phone = entity.phone,
            wire = entity.wire,
            location = entity.location
        )

        return BaseResponse (
            status = HttpStatus.OK.value(),
            success = true,
            message = "프로필 불러오기 성공",
            data = profile
        )

    }

}