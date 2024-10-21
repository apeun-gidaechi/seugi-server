package com.seugi.api.domain.profile.application.port.`in`

import com.seugi.api.domain.workspace.domain.enums.WorkspaceRole


interface ManageProfileUseCase {

    fun manageProfile(memberId: Long, workspaceId: String, permission: WorkspaceRole)
    fun deleteProfile(memberId: Long, workspaceId: String)

}