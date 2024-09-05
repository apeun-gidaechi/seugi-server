package com.seugi.api.domain.profile.application.port.`in`

import com.seugi.api.domain.workspace.domain.enums.WorkspaceRole


interface CreateProfileUseCase {

    fun createProfile(memberId: Long, workspaceId: String, permission: WorkspaceRole)

}