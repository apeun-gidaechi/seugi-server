package com.seugi.api.domain.workspace.presentation.dto.request

import com.seugi.api.domain.workspace.domain.enums.WorkspaceRole

data class GetWaitListRequest (
    val role: WorkspaceRole,
    val workspaceId: String
)