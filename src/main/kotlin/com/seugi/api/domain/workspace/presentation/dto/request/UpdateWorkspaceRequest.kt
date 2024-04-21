package com.seugi.api.domain.workspace.presentation.dto.request

data class UpdateWorkspaceRequest (
    val workspaceId: String = "",
    val workspaceName: String = "",
    val workspaceImgUrl: String = ""
)