package com.seugi.api.domain.workspace.presentation.dto.request

data class CreateWorkspaceRequest (
    val workspaceName: String = "",
    val workspaceImageUrl: String = "",
)