package com.seugi.api.domain.profile.application.service

import com.seugi.api.domain.profile.adapter.`in`.request.EditProfileRequest
import com.seugi.api.domain.profile.application.model.Profile
import com.seugi.api.domain.profile.application.port.`in`.EditProfileUseCase
import com.seugi.api.domain.profile.application.port.out.LoadProfilePort
import com.seugi.api.domain.profile.application.port.out.SaveProfilePort
import com.seugi.api.global.response.BaseResponse
import org.springframework.stereotype.Service

@Service
class EditProfileService (
    private val loadProfilePort: LoadProfilePort,
    private val saveProfilePort: SaveProfilePort
) : EditProfileUseCase {

    override fun editProfile(dto: EditProfileRequest, workspaceId: String, id: Long): BaseResponse<Unit> {
        val profile: Profile = loadProfilePort.loadProfile(id, workspaceId)

        profile.editProfile(dto)

        saveProfilePort.saveProfile(profile)

        return BaseResponse (
            message = "프로필 수정 성공 !!",
        )
    }

}