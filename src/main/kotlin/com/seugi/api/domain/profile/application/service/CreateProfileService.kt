package com.seugi.api.domain.profile.application.service

import com.seugi.api.domain.member.application.port.out.LoadMemberPort
import com.seugi.api.domain.profile.application.model.Profile
import com.seugi.api.domain.profile.application.model.value.ProfilePermission
import com.seugi.api.domain.profile.application.model.value.ProfileWorkspaceId
import com.seugi.api.domain.profile.application.port.`in`.CreateProfileUseCase
import com.seugi.api.domain.profile.application.port.out.ExistProfilePort
import com.seugi.api.domain.profile.application.port.out.SaveProfilePort
import com.seugi.api.domain.workspace.domain.enums.WorkspaceRole
import org.springframework.stereotype.Service

@Service
class CreateProfileService (
    private val existProfilePort: ExistProfilePort,
    private val loadMemberPort: LoadMemberPort,
    private val saveProfilePort: SaveProfilePort
) : CreateProfileUseCase {

    override fun createProfile(memberId: Long, workspaceId: String, permission: WorkspaceRole) {
        if (!existProfilePort.existProfile(memberId, workspaceId)) {
            val member = loadMemberPort.loadMemberWithId(memberId)

            val profile = Profile(
                member = member,
                workspaceId = ProfileWorkspaceId(workspaceId),
                permission = ProfilePermission(permission)
            )

            saveProfilePort.saveProfile(profile)
        }
    }

}
