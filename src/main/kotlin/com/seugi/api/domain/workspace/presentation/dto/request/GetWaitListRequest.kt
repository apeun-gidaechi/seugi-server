package com.seugi.api.domain.workspace.presentation.dto.request

import com.seugi.api.domain.workspace.domain.enums.WorkspaceRole

data class GetWaitListRequest (
    val role: WorkspaceRole = WorkspaceRole.STUDENT,
    val workspaceId: String = ""
)