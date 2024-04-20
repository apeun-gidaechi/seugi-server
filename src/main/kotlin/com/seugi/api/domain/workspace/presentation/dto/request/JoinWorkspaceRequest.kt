package com.seugi.api.domain.workspace.presentation.dto.request

import com.seugi.api.domain.workspace.domain.enums.WorkspaceRole

data class JoinWorkspaceRequest (
    val workspaceId: String = "",
    val workspaceCode: String = "",
    val role: WorkspaceRole = WorkspaceRole.STUDENT
)