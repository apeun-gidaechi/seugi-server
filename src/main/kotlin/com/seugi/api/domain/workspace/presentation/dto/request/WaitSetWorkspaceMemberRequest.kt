package com.seugi.api.domain.workspace.presentation.dto.request

import com.seugi.api.domain.workspace.domain.enums.WorkspaceRole

data class WaitSetWorkspaceMemberRequest (
    val workspaceId: String = "",
    val approvalUserSet: Set<Long> = emptySet(),
    val role: WorkspaceRole = WorkspaceRole.STUDENT
)