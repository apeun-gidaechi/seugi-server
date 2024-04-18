package com.seugi.api.domain.workspace.presentation.dto.request

data class CreateWorkspace (
    val workspaceName: String,
    val workspaceImageUrl: String,
)