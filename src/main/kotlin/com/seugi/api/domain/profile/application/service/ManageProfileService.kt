package com.seugi.api.domain.profile.application.service

import com.seugi.api.domain.member.domain.model.Member
import com.seugi.api.domain.member.service.MemberService
import com.seugi.api.domain.profile.application.model.Profile
import com.seugi.api.domain.profile.application.model.value.ProfilePermission
import com.seugi.api.domain.profile.application.model.value.ProfileWorkspaceId
import com.seugi.api.domain.profile.application.port.`in`.ManageProfileUseCase
import com.seugi.api.domain.profile.application.port.out.DeleteProfileUseCase
import com.seugi.api.domain.profile.application.port.out.ExistProfilePort
import com.seugi.api.domain.profile.application.port.out.LoadProfilePort
import com.seugi.api.domain.profile.application.port.out.SaveProfilePort
import com.seugi.api.domain.workspace.domain.enums.WorkspaceRole
import org.springframework.stereotype.Service

@Service
class ManageProfileService(
    private val existProfilePort: ExistProfilePort,
    private val memberService: MemberService,
    private val loaProfilePort: LoadProfilePort,
    private val saveProfilePort: SaveProfilePort,
    private val deleteProfileUseCase: DeleteProfileUseCase,
) : ManageProfileUseCase {

    override fun manageProfile(memberId: Long, workspaceId: String, permission: WorkspaceRole) {
        val member = memberService.findById(memberId)

        if (existProfilePort.existProfile(memberId, workspaceId)) {
            updateExistingProfile(memberId, workspaceId, permission)
        } else {
            createNewProfile(member, workspaceId, permission)
        }
    }

    override fun deleteProfile(memberId: Long, workspaceId: String) {
        deleteProfileUseCase.deleteProfile(memberId, workspaceId)
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
