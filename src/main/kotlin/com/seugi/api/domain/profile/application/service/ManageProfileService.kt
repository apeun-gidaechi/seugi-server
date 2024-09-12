package com.seugi.api.domain.profile.application.service

import com.seugi.api.domain.member.application.model.Member
import com.seugi.api.domain.member.application.port.out.LoadMemberPort
import com.seugi.api.domain.profile.application.model.Profile
import com.seugi.api.domain.profile.application.model.value.ProfilePermission
import com.seugi.api.domain.profile.application.model.value.ProfileWorkspaceId
import com.seugi.api.domain.profile.application.port.`in`.ManageProfileUseCase
import com.seugi.api.domain.profile.application.port.out.ExistProfilePort
import com.seugi.api.domain.profile.application.port.out.LoadProfilePort
import com.seugi.api.domain.profile.application.port.out.SaveProfilePort
import com.seugi.api.domain.workspace.domain.enums.WorkspaceRole
import org.springframework.stereotype.Service

@Service
class ManageProfileService(
    private val existProfilePort: ExistProfilePort,
    private val loadMemberPort: LoadMemberPort,
    private val loaProfilePort: LoadProfilePort,
    private val saveProfilePort: SaveProfilePort,
) : ManageProfileUseCase {

    override fun manageProfile(memberId: Long, workspaceId: String, permission: WorkspaceRole) {
        val member = loadMemberPort.loadMemberWithId(memberId)

        if (existProfilePort.existProfile(memberId, workspaceId)) {
            updateExistingProfile(memberId, workspaceId, permission)
        } else {
            createNewProfile(member, workspaceId, permission)
        }
    }

    private fun updateExistingProfile(memberId: Long, workspaceId: String, permission: WorkspaceRole) {
        val profile = loaProfilePort.loadProfile(memberId, workspaceId)
        profile.changeRole(ProfilePermission(permission))
        saveProfilePort.saveProfile(profile)
    }

    private fun createNewProfile(member: Member, workspaceId: String, permission: WorkspaceRole) {
        val profile = Profile(
            member = member,
            workspaceId = ProfileWorkspaceId(workspaceId),
            permission = ProfilePermission(permission)
        )
        saveProfilePort.saveProfile(profile)
    }

}
