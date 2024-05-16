package com.seugi.api.domain.profile.application.service

import com.seugi.api.domain.profile.adapter.`in`.request.EditProfileDTO
import com.seugi.api.domain.profile.application.port.`in`.EditProfileUseCase
import com.seugi.api.domain.profile.application.port.out.LoadProfilePort
import com.seugi.api.domain.profile.application.port.out.SaveProfilePort
import com.seugi.api.global.response.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class EditProfileService (
    private val loadProfilePort: LoadProfilePort,
    private val saveProfilePort: SaveProfilePort
) : EditProfileUseCase {

    override fun editProfile(dto: EditProfileDTO, workspaceId: String, id: Long): BaseResponse<Unit> {
        val profile = loadProfilePort.loadProfile(id, workspaceId)

        profile.status = dto.status
        profile.nick = dto.nick
        profile.belong = dto.belong
        profile.location = dto.location
        profile.phone = dto.phone
        profile.spot = dto.spot
        profile.wire = dto.wire

        saveProfilePort.saveProfile(profile)

        return BaseResponse (
            status = HttpStatus.OK.value(),
            success = true,
            message = "프로필 수정 성공 !!",
        )

    }
}